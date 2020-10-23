/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  manohisoa
 * Created: Aug 28, 2020
 */
CREATE SEQUENCE seq_aeroport;
CREATE TABLE aeroport(
  id VARCHAR(50) PRIMARY KEY,
  ville VARCHAR(60)
);
insert into aeroport values('AER0001','Tamatave');
insert into aeroport values('AER0002','Tana');
CREATE SEQUENCE seq_avion;
CREATE TABLE avion(
  id VARCHAR(50) PRIMARY KEY,
  reference VARCHAR(60),
  longueurDecol decimal(10,2),
  longueurAtter decimal(10,2)
);
insert into avion values('AVN0001','A340',1500,1500);
insert into avion values('AVN0002','ATR320',1500,1500);
insert into avion values('AVN0003','B770',800,800);

--(temps de degagement:(min avant jusqua miala eo am piste)
CREATE SEQUENCE seq_piste;
CREATE TABLE piste(
  id VARCHAR(50) PRIMARY KEY,
  aeroport VARCHAR(50) REFERENCES aeroport,
  longueur decimal(10,2),
  tempsDegagement INTERVAL,
  pointApiste VARCHAR(50),
  pointBpiste VARCHAR(50),
  anglepiste decimal(10,2)
);

insert into piste values('PST0001','AER0001',2000,'00:30:00','1;1','5,3',26);
insert into piste values('PST0002','AER0001',1000,'00:30:00','-5;4','-2,1',-45);
insert into piste values('PST0003','AER0001',500,'00:30:00','0;-2','0,-5',90);


-- proposer
--  liste vol miditra sy mvoka ao anaty (aeroport,piste)
--  mproposer en fonction vol ananana
--  le duree mila antanina
CREATE SEQUENCE seq_trajet;
CREATE TABLE trajet(
  id VARCHAR(50) PRIMARY KEY,
  depart VARCHAR(50) REFERENCES aeroport,
  arrivee VARCHAR(50) REFERENCES aeroport,
  dureeestimer INTERVAL
);
-- insert into trajet values('TRJ0001','AER0001','AER0002','01:00:00');
insert into trajet values('TRJ0001','AER0001','AER0002','01:00:00');
--1.16.1
 --decalage
CREATE SEQUENCE seq_vol;
CREATE TABLE vol(
  id VARCHAR(50) PRIMARY KEY,
  trajet VARCHAR(50) REFERENCES trajet,
  avion VARCHAR(50) REFERENCES avion,
  duree INTERVAL,
  datedepart timestamp,
  etat integer,
  pointAvol VARCHAR(50),
  pointBvol VARCHAR(50),
  anglevol decimal(10,2)
);



insert into vol values('VL0001','TRJ0001','AVN0001','01:00:00','2020-09-09 12:00:00',1,'0;0','0,0',90);
insert into vol values('VL0002','TRJ0001','AVN0001','01:00:00','2020-09-09 12:05:00',1,'0;0','0,0',35);

insert into vol values('VL0003','TRJ0001','AVN0003','01:00:00','2020-09-09 12:10:00',1,'0;0','0,0',22);
insert into vol values('VL0004','TRJ0001','AVN0002','01:00:00','2020-09-09 12:15:00',1,'0;0','0,0',15);

insert into vol values('VL0005','TRJ0001','AVN0003','01:00:00','2020-09-09 12:20:00',1,'0;0','0,0',30);
insert into vol values('VL0006','TRJ0001','AVN0003','01:00:00','2020-09-09 12:25:00',1,'0;0','0,0',35);

insert into vol values('VL0007','TRJ0001','AVN0001','01:00:00','2020-09-09 12:30:00',1,'0;0','0,0',90);
insert into vol values('VL0008','TRJ0001','AVN0003','01:00:00','2020-09-09 12:35:00',1,'0;0','0,0',80);

insert into vol values('VL0009','TRJ0001','AVN0002','01:00:00','2020-09-09 12:40:00',1,'0;0','0,0',22);
insert into vol values('VL00010','TRJ0001','AVN0002','01:00:00','2020-09-09 12:45:00',1,'0;0','0,0',50);


--update vol set etat=10 where id='VL0001';
--update vol set datedepart='2020-09-06 15:00:00' where id='VL0001';
CREATE SEQUENCE seq_attributionpiste;
create table attributionPiste(
    id VARCHAR(50) PRIMARY KEY,
    vol VARCHAR(50) REFERENCES vol,
    piste VARCHAR(50) REFERENCES piste,
    dateVrai timestamp,
    dateproposition timestamp,
    etat integer
);

create table decalage(
    vol VARCHAR(50) REFERENCES vol,
    decalageManuel INTERVAL,
    decalage INTERVAL
);
insert into decalage values('VL0001','00:15:00','00:00:00');
delete from decalage where decalageManuel='00:05:00' and vol='VL0001';
CREATE SEQUENCE seq_effectif;
create table effectif(
    id VARCHAR(50) PRIMARY KEY,
    vol VARCHAR(50) REFERENCES vol,
    dateEffectif timestamp
);
--insert into effectif values('001','VL0001',now());

create or replace view vol_all_decalage as
    select vol.*,
            case
                when decalage.decalageManuel is null then '00:00:00'
                else decalage.decalageManuel
            end decalageManuel,
            case
                when decalage.decalage is null then '00:00:00'
                else decalage.decalage
            end decalage
    from vol left join decalage on vol.id=decalage.vol
where vol.etat!=10 and vol.id not in(select vol from effectif);


create view sum_decalage_tsotra_vol as
    select id,sum(decalage) as decaltsotra
from vol_all_decalage group by id;


create or replace view sum_decalage_manuel_vol as
    select id,sum(decalageManuel)as decalmanuel from vol_all_decalage group by id;

create view sum_decalage_vol as
    select sum_decalage_tsotra_vol.id,(decaltsotra+decalmanuel) as somme,decalmanuel from
 sum_decalage_tsotra_vol join sum_decalage_manuel_vol on sum_decalage_tsotra_vol.id=sum_decalage_manuel_vol.id;

create view vol_decalage as
     select vol.*,somme as sommeDecalage,decalmanuel
from vol join sum_decalage_vol on vol.id=sum_decalage_vol.id;

--amboarina
create view vol_avion_trajet as
    select vol_decalage.*,
            avion.id idavion,
            avion.reference,
            avion.longueurDecol,
            avion.longueurAtter,
            trajet.id idtrajet,
            trajet.depart,
            trajet.arrivee
from vol_decalage join avion on vol_decalage.avion=avion.id join trajet on trajet.id=vol_decalage.trajet;

create view aeroport_pistes as
    select aeroport.*,
piste.id idpiste,
  piste.longueur,
  piste.tempsDegagement,
  piste.pointApiste,
  piste.pointBpiste,
  piste.anglepiste
    from aeroport join piste on aeroport.id=piste.aeroport;

create or replace view decol as
select vol_avion_trajet.id  ,
 vol_avion_trajet.trajet,
 vol_avion_trajet.avion,
 vol_avion_trajet.duree,
 vol_avion_trajet.datedepart+vol_avion_trajet.sommedecalage datedepart,
 vol_avion_trajet.etat,
 vol_avion_trajet.pointAvol,
 vol_avion_trajet.pointBvol,
 vol_avion_trajet.anglevol,
 vol_avion_trajet.sommedecalage,
 vol_avion_trajet.decalmanuel,
 vol_avion_trajet.idavion,
 vol_avion_trajet.reference,
 vol_avion_trajet.longueurdecol,
 vol_avion_trajet.longueuratter,
 vol_avion_trajet.idtrajet,
 vol_avion_trajet.depart,
vol_avion_trajet.arrivee,
aeroport_pistes.ville,
aeroport_pistes.idpiste,
aeroport_pistes.longueur,
aeroport_pistes.tempsdegagement,
aeroport_pistes.pointApiste,
aeroport_pistes.pointBpiste,
aeroport_pistes.anglepiste,
case
    when vol_avion_trajet.depart=aeroport_pistes.id then 'dec'
    end typevol,
case
    when vol_avion_trajet.depart=aeroport_pistes.id then aeroport_pistes.id
    end toerana

 from vol_avion_trajet join aeroport_pistes
on  vol_avion_trajet.depart=aeroport_pistes.id where aeroport_pistes.longueur > vol_avion_trajet.longueurdecol order by id;

create or replace view atter as
select vol_avion_trajet.id  ,
 vol_avion_trajet.trajet,
 vol_avion_trajet.avion,
 vol_avion_trajet.duree,
 vol_avion_trajet.datedepart+vol_avion_trajet.duree+vol_avion_trajet.sommedecalage datedepart,
 vol_avion_trajet.etat,
 vol_avion_trajet.pointAvol,
 vol_avion_trajet.pointBvol,
 vol_avion_trajet.anglevol,
 vol_avion_trajet.sommedecalage,
 vol_avion_trajet.decalmanuel,
 vol_avion_trajet.idavion,
 vol_avion_trajet.reference,
 vol_avion_trajet.longueurdecol,
 vol_avion_trajet.longueuratter,
 vol_avion_trajet.idtrajet,
 vol_avion_trajet.depart,
vol_avion_trajet.arrivee,
aeroport_pistes.ville,
aeroport_pistes.idpiste,
aeroport_pistes.longueur,
aeroport_pistes.tempsdegagement,
aeroport_pistes.pointApiste,
aeroport_pistes.pointBpiste,
aeroport_pistes.anglepiste,
case
    when vol_avion_trajet.arrivee=aeroport_pistes.id then 'att'
    end typevol,
case
    when vol_avion_trajet.arrivee=aeroport_pistes.id then aeroport_pistes.id
    end toerana
from vol_avion_trajet join aeroport_pistes
on  vol_avion_trajet.arrivee=aeroport_pistes.id where aeroport_pistes.longueur > vol_avion_trajet.longueuratter order by id;

create or replace view vol_piste as
select * from(select * from decol union
select * from atter) as foo;

select * from vol_piste where (typevol='dec' or typevol='att') and toerana='AER0001'
and (datedepart>='2020-09-09 00:00:00' and datedepart <= '2020-09-09 12:40:00') order by id;



