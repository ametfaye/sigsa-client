--SENSIBILITE
INSERT INTO sensibilite (id,active,code,libelle) values (1,1,'ORD','ordinaire');
INSERT INTO sensibilite (id,active,code,libelle) values (2,1,'CONF','confidentiel');
INSERT INTO sensibilite (id,active,code,libelle) values (3,1,'SEC','secret');

--PRIORITE
INSERT INTO priorite (id,active,code,libelle) values (1,1,'ORD','ordinaire');
INSERT INTO priorite (id,active,code,libelle) values (2,1,'urg','urgent');
INSERT INTO priorite (id,active,code,libelle) values (3,1,'Turg','trés urgent');

--ETAT
INSERT INTO etat(id_etat, code, libelle) VALUES (1, 'E1', 'Enregistré');
INSERT INTO etat(id_etat, code, libelle) VALUES (2, 'E2', 'Réceptionné');
INSERT INTO etat(id_etat, code, libelle) VALUES (3, 'E3', 'Imputé');
INSERT INTO etat(id_etat, code, libelle) VALUES (4, 'E4', 'Traité');
INSERT INTO etat(id_etat, code, libelle) VALUES (5, 'E5', 'Classé');

--Type-Evenment
INSERT INTO type_evenement (id_event,active,code_event,libelle_event) values(1,1,'B1','Enrégistrement');
INSERT INTO type_evenement (id_event,active,code_event,libelle_event) values(2,1,'B2','Réception');
INSERT INTO type_evenement (id_event,active,code_event,libelle_event) values(3,1,'B3','Imputation');
INSERT INTO type_evenement (id_event,active,code_event,libelle_event) values(4,1,'B4','Classement');
INSERT INTO type_evenement (id_event,active,code_event,libelle_event) values(5,1,'B5','Traitement');

--ENTITE
INSERT INTO entite (id_entite,active,code_entite,libelle_entite) values(1,1,'Ep','Entité publique');
INSERT INTO entite (id_entite,active,code_entite,libelle_entite) values(2,1,'Am','Ambassade ');
INSERT INTO entite (id_entite,active,code_entite,libelle_entite) values(3,1,'Or','Organisation Internationales');
INSERT INTO entite (id_entite,active,code_entite,libelle_entite) values(4,1,'Ptf','PTF');
INSERT INTO entite (id_entite,active,code_entite,libelle_entite) values(5,1,'EntiteMFB','Entite du MFB');

--TYPE-COURIER
INSERT INTO type_courrier (id_type_cour,active,code,libelle) values(1,1,'C1','Lettres');
INSERT INTO type_courrier (id_type_cour,active,code,libelle) values(2,1,'C2','Emails');
INSERT INTO type_courrier (id_type_cour,active,code,libelle) values(3,1,'C3','Colis');
INSERT INTO type_courrier (id_type_cour,active,code,libelle) values(4,1,'C4','Télécopies');


--SERVICES

--CABINET
INSERT INTO services (id_service,active,code,libelle,entite_id) values(1,1,'IGD','Inspection Générale des Finances',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(2,1,'CNTIF','Cellule Nationale de Traitement des Informations Financiéres',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(3,1,'CDC','Cellule de communication',5);
--SG
INSERT INTO services (id_service,active,code,libelle,entite_id) values(4,1,'PCRBF','Projet de cordinationation des réformes budgétaires et financiéres',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(5,1,'SG','Sécretariat Général',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(6,1,'CEP','Céllule d''Etude et de Planifiacation',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(7,1,'CPMP','Céllule de Passation des Marchés Publics',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(8,1,'BCC','Bureau du Courier COMMUN',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(9,1,'CAJ','Cellule des Affaires Jurid_destiques',5);
--DG
INSERT INTO services (id_service,active,code,libelle,entite_id) values(10,1,'DGCPT','Direction Générale de la Comptabilté Publique et du Trésor',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(11,1,'DGD','Direction Générale des Douanes',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(12,1,'DGID','Direction Générale des Impôts et Domaines',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(13,1,'DGB','Direction Générale du Budget',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(14,1,'DGSF','Direction Générale du Secteur Financier',5);
--AD
INSERT INTO services (id_service,active,code,libelle,entite_id) values(15,1,'DTAI','Direction du Traitement Automatique de l''Information',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(16,1,'DCMP','Direction Central des Marchés publics',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(17,1,'AJE','Agence Judiciare de l''Etat',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(18,1,'DAGE','Direction de l''Admistration Générale et de L''Equipement',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(19,1,'DRH','Direction des Ressources Humaines',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(20,1,'FSE','Fonds spécial de soutien au secteur de l''Energie',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(21,1,'OQSF','Observatoire sur la Qualité des Services financiers',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(22,1,'OQSF','Observatoire sur la Qualité des Services financiers',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(23,1,'FONGIP','Fonds de Garantie des Investissements prioritaires',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(24,1,'FONSIS','Fonds Souverain d’Investissements Stratégiques',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(25,1,'ONECA','Ordre national des Experts comptables et comptables agréés',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(26,1,'CDC','Caisse des Dépôts et de Consignations',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(27,1,'','',5);
--Autres ADMINS
INSERT INTO services (id_service,active,code,libelle,entite_id) values(28,1,'LONASE','Loterie nationale sénégalaise',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(29,1,'CBAO','Compagnie bancaire pour l’Afrique occid_destentale',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(30,1,'CS','Crédit du Sénégal',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(31,1,'BICIS','Banque internationale pour le Commerce et l’Industrie du Sénégal',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(32,1,'AAS','AXA Assurances Sénégal',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(33,1,'AMSA Assurances','AMSA Assurances',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(34,1,'BHS','Banque de l’Habitat du Sénégal',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(35,1,'CNCAS',' Caisse nationale de Crédit agricole du Sénégal',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(36,1,'BIS','Banque islamique du Sénégal',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(37,1,'SENRE','Société sénégalaise de Réassurances',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(38,1,'SNR','Société nationale de Recouvrement',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(39,1,'FGA','Fonds de Garantie automobile',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(40,1,'SONAC','Société nationale d’Assurance et de Crédit',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(41,1,'CNAA','Compagnie nationale d’Assurance Agricole',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(42,1,'BNDE','Banque nationale de Développement économique',5);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(43,1,'SOGIP SA','Société de Gestion des Infrastructures publiques dans les Pôles Urbains de Diamniadio et du Lac Rose',5);

INSERT INTO services (id_service,active,code,libelle,entite_id) values(44,1,'AMUSA','Ambassade des USA',2);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(45,1,'FMI','Fonds Monétaires d Ivestissements',4);
INSERT INTO services (id_service,active,code,libelle,entite_id) values(46,1,'BCEAO','Banque Centrale Des Etats de l Afrique de l Ouest',4);

--WORFLOW
INSERT INTO workflow (id,etat_init,etat_suivant,active,id_event) values(1,1,3,3);
INSERT INTO workflow (id,etat_init,etat_suivant,active,id_event) values(2,3,2,1);
INSERT INTO workflow (id,etat_init,etat_suivant,active,id_event) values(3,2,3,1);
INSERT INTO workflow (id,etat_init,etat_suivant,active,id_event) values(4,2,4,1);
INSERT INTO workflow (id,etat_init,etat_suivant,active,id_event) values(5,4,5,1);

DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 5000
  CACHE 1;
GRANT ALL PRIVILEGES ON SEQUENCE hibernate_sequence TO postgres;