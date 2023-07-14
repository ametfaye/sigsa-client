------------------------------   Campagne de test 2016 2017 -------------------------------------------------------------
------Campagne --------
-------------------------------------------------------------------------------------------------------------------------
--INSERT INTO pgca_campagneagricole(pgca_idca, datefermeture, dateouverture, libelle,  statut) VALUES (1, '2015-01-01', '2015-10-10', 'Campagne Agricole 2015', 1);     
--INSERT INTO pgca_campagneagricole(pgca_idca, datefermeture, dateouverture, libelle,  statut) VALUES (2, '2016-01-01', '2016-10-10', 'Campagne Agricole 2016',  2);    
------Campagne---------
-------------------------------------------------------------------------------------------------------------------------

------------------------------   Programme de test 2016 2017 -------------------------------------------------------------
------Campagne --------
-------------------------------------------------------------------------------------------------------------------------   
--INSERT INTO pgca_programme(pgca_idprog, libelle, statut, dateOuverture,  dateFermeture, pgca_idca) VALUES (0, 'Programme Sedab', '0', '2015-03-01', '2016-09-06', 1);
--INSERT INTO pgca_programme(pgca_idprog, libelle, statut, dateOuverture,  dateFermeture, pgca_idca) VALUES (1, 'Programme Arachide', '1', '2015-03-01', '2016-09-06', 1);
--INSERT INTO pgca_programme(pgca_idprog, libelle, statut, dateOuverture,  dateFermeture, pgca_idca) VALUES (2, 'Programme Semences', '1', '2015-04-01', '2016-12-10', 1);      
------Campagne ---------
-------------------------------------------------------------------------------------------------------------------------    

------Pendant l'appro , les intrannt ont une campagne par defaut et programme par defaut ---------
-------------------------------------------------------------------------------------------------------------------------    
INSERT INTO pgca_campagneagricole(pgca_idca, datefermeture, dateouverture, libelle,  statut) VALUES (0, '2015-01-01', '2120-10-10', 'Campagne par défaut', 1);     
INSERT INTO pgca_programme(pgca_idprog, libelle, statut, dateOuverture,  dateFermeture, pgca_idca) VALUES (0, 'Activités générales Sedab', '1', '2015-03-01', '2120-01-01', 0);




------Fournisseurs --------
-------------------------------------------------------------------------------------------------------------------------   
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (0, 'Stock résiduel SEDAB', 'SEDAB SENEGAl', '338675645');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (1, 'Industrie Chimique du Sénégal', 'Issa Diop', '338675645');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (2, 'Transcontinental transit', 'Ameth Mbaye', '773457688');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (3, 'Louis Dreyfus Commandites', 'Chrsitelle Leste', '0033646568330');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (4, 'Timac Agros', 'Christian Lamy', '765654534');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (5, 'Trammo DDC', 'Jean Luc', '775609833');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (6, 'Agriplus', 'Amadou Camara', '776568756');
INSERT INTO pgca_fournisseur(four_id, libelle, representantcivil, representanttelephone) VALUES (7, 'Nafaso', 'Cheikh Guiti ', '776568756');

------Fournisseurs ---------
-------------------------------------------------------------------------------------------------------------------------    


            
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (1, 'SEMENCES De Riz', 1 ,  'semenceRiz.jpg'); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (2, 'SEMENCES DE MAÏS' , 1, 'catmais.jpg' ); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (3, 'SEMENCES D ARACHIDES' , 1, 'catArachide.png' ); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (4, 'SEMENCES MIL' , 1 , 'catMil.png' ); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (5, 'SEMENCES DE NIEBE' , 1 , 'catNiebe.png' ); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (6, 'ENGRAIS' , 1 , 'engrais2.jpg' ); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (7, 'HERBICIDES' , 4, 'herbicides.jpg'); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (8, 'Materiels', 3, 'materiels.jpg'); 
INSERT INTO pgca_referentielTypeIntrant(typeIntrantId, libelle, uniteDeMesure , pictoIntrant) VALUES (9, 'Autres', 3 , 'autres.png');

------Type INTRANT---------
-------------------------------------------------------------------------------------------------------------------------

------------------------------   Réfrentiels des intrants --------------------------------------------------------------
------INRANT---------
-------------------------------------------------------------------------------------------------------------------------
--- Semences
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (2,  1, 'SAHEL 1108');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (3,  1, 'SAHEL 201');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (4,  1, 'SAHEL 134');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (5,  1, 'SAHEL 328');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (6,  1, 'SAHEL 202');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (7,  1, 'FKR 19');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (8,  1, 'WAR 77-3-2-2');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (9,  1, 'BG 90-2');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (10, 1, 'TS2');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (11, 1, 'FKR 45 N');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (12, 1, 'NERICA 1');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (13, 1, 'NERICA 2');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (14, 1, 'NERICA 3');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (15, 1, 'NERICA 4');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (16, 1, 'NERICA 5');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (17, 1, 'NERICA 6');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (18, 1, 'NERICA S44');
----  Intrant from catalogue officiel
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (100, 1, 'ITA 150');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (101, 1, 'WAR 56 50');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (102, 1, 'DJ 684-D');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (103, 1, 'DJ 11-509');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (104, 1, 'DJ 12-519');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (105, 1, 'BW 248-1');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (106, 1, 'BR 51-46-5');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (107, 1, 'ITA 123');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (109, 1, 'TOX 728-1');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (110, 1, 'ROK 5');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (111, 1, 'WAR 1');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (113, 1, 'WAR 81-2-1-3-2');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (114, 1, 'IKONG PAO');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (115, 1, 'IR 8');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (116, 1, 'Jaya');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (117, 1, 'IR 1529-680-3');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (118, 1, 'IR 442');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (119, 1, 'KH 998');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (120, 1, 'KWAN SHE SHUMG');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (121, 1, 'SAHEL 108');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (122, 1, 'SAHEL 159');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (123, 1, 'SAHEL 208');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (124, 1, 'SAHEL 209');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (125, 1, 'SAHEL 210');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (126, 1, 'SAHEL 209');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (1277, 1, 'SAHEL 177');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (1267, 1, 'SAHEL 217');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (127, 1, 'SAHEL 177');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (128, 1, 'SAHEL 222');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (129, 1, 'SAHEL 305');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (130, 1, 'SAHEL 222');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (131, 1, 'SAHEL 317');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (133, 1, 'SAHEL 317');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (134, 1, 'NERICA S-19');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (135, 1, 'NERICA S-21');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (136, 1, 'NERICA S-36');


---- Semence de Mais
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (19, 2,  'OBATAMPA');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (320, 2,  'SWAN');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (321, 2,  'Xeweul Gi');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (322, 2,  'NOOR 96');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (323, 2,  'DOO MER');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (324, 2,  'Sooror');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (325, 2,  'Gaawnaa');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (326, 2,  'Jaboot');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (327, 2,  'Goor Yomboul');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (328, 2,  'Yaayi Seex');


---- Semence d'arachide
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (21, 3,  'FLEURE 11');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (22, 3,  '55-33');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (23, 3,  '55-347');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (24, 3,  '73-00');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (25, 3,  '69-101');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (525, 3,  '28-206');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (526, 3,  '776-A');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (527, 3,  '55-437');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (528, 3,  '57-313');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (529, 3,  '57-422');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (531, 3,  '77-27');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (532, 3,  '73-28');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (533, 3,  '73-30');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (534, 3,  '73-33');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (535, 3,  'GC 8-35');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (536, 3,  '78 937');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (537, 3,  'SRV 1-19');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (538, 3,  '73 9-11');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (539, 3,  'GH 119-20');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (540, 3,  'PC 79-79');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (541, 3,  'H 75-0');





---- Semence Niebe
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (26, 5,  'MELAKH');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (27, 5,  'MOUGNE');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (28, 5,  'AERLY TAÏ');
---- Angrais
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (29, 6,  'UREE');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (30, 6,  'DAP');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (31, 6,  'DSP');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (32, 6,  'NPK 15-15-15');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (33, 6,  'NPK 10-10-20');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (34, 6,  'NPK 06-20-10');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (35, 6,  'NPK 09-23-30');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (36, 6,  'NPK 15-10-10');
---- HERBICIDES
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (37, 7,  'PROPANIL');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (38, 7,  'WEEDON');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (39, 7,  'OXADIAZON');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (40, 7,  'RUND UP');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (41, 7,  'GLYPHOSATE');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (42, 7,  'IKOKADIGNE');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (43, 7,  'ALLIGATOR');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (44, 7,  'EUREKA');
---- Autres type
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (45, 8,  'PULVERISATEUR');

---- Semence de mil
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (226, 4,  'SOUNA 3');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (227, 4,  'IBV 8001');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (228, 4,  'IBV 8004');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (229, 4,  'IBV 8402');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (230, 4,  'ISMI94 07');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (231, 4,  'GAWANE');
INSERT INTO pgca_referentielIntrant(refIntrantId , typeIntrantId, libelle) VALUES (232, 4,  'THIALACK 2');




------INTRANT---------
-------------------------------------------------------------------------------------------------------------------------



 
------------------------------  ADRESSE ---------------------------------------------------------------------------------
------ADRESSE---------
-------------------------------------------------------------------------------------------------------------------------
--DIRECTION GENERAL
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (1, 99999, 'Beau Rivage', 'Km 5, Bd du Centenaire de la Commune', 'DAKAR', 1, 1, 2);
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (100, 99999, 'Ministére de l agriculture', '23 Avenue Leopold sedar Senghor', 'DAKAR', 12, 1, 2);


------------- Zone  Nord 
--** Coordonateur
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (2, 99999, 'Sedab - Zone Nord', 'RICHARD TOLL', 'Saint Louis', 10,  30, 349);
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (3, 99999, 'Sedab - NDIOUGOU', 'RICHARD TOLL', 'Saint Louis', 10,  30, 349);
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (4, 99999, 'Sedab - ROSS BETHIO', 'RICHARD TOLL', 'Saint Louis', 10,  30, 352);

INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (5, 99999, 'Sedab - Zone Sud' ,'Kolda', 'Kolda', 6, 21, 41);
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (6, 99999, 'Sedab - Centre EST', 'SibaSor', 'Sibasor', 5, 15, 167);
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (7, 99999, 'Sedab - Centre OUEST', 'TOUBA', 'TOUBA', 2, 7, 79);
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id)  VALUES (8, 99999, 'Sedab - SEDHIOU', 'SEDHIOU', 'SEDHIOU', 11, 35, 415);

------FIN ADRESSE ------
-------------------------------------------------------------------------------------------------------------------------




------------------------------  CONTACT ---------------------------------------------------------------------------------
------CONTACT---------
-------------------------------------------------------------------------------------------------------------------------

--DIRECTION GENERAL
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (1, 'Contact Manager', '776651190', '33 832 56 09 ', 'sedabsenegal@gmail.com', '33 832 27 94', 'www.sedabsenegal.sn');
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (111, 'Manager', '776651190', '33 832 56 09 ', 'modouthiam@sedabsenegal.com', '33 832 27 94', 'www.sedabsenegal.sn');
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (112, 'Manager', '776651190', '33 832 56 09 ', 'moulayekande@sedabsenegal.com', '33 832 27 94', 'www.sedabsenegal.sn');


------------- Zone  Nord 
--** Coordonateur
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (2, 'Charles Antoine Large', '776154936', '33 976 56 88 ', 'charleslarge@sedabsenegal.com', '33 832 66 04', 'www.sedab.sn');
--** Superviseur
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (3, 'Alassane Kande', '779017083', '33 954 12 89 ', 'sedabnord@live.com', '33 822 27 04', 'www.sedab.sn');
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (4, 'Antoine Diémé', '774404855', '33 954 02 00 ', 'tony.dieme@hotmail.com', '33 822 27 04', 'www.sedab.sn');
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (5, 'Mbaye Ndiaye', '774573304', '33 954 12 89 ', 'sedabnord@live.com', '33 822 27 04', 'www.sedab.sn');
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (6, 'Jean Ndiaye', '775390276', '33 954 12 89 ', 'sedabnord@live.com', '33 822 27 04', 'www.sedab.sn');
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (7, 'Ameth Large', '777268452', '33 954 12 89 ', 'large2011@hotmail.com', '33 822 27 04', 'www.sedab.sn');

----------- Zone  SUD
--** Coordonateur
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (8, 'DAHA BALDE', '774203225', '33 555 12 67', 'sedabsud@live.fr', '33 844 37 33', 'www.thico.sn');

--------- Zone CENTRE EST
--** Coordonateur
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (9, 'Vieux Cisse', '775207749', '33 855 12 67', 'sedabcentre@hotmai.com', '33 844 37 33', 'www.thico.sn');

--------- Zone CENTRE OUEST
--** Coordonateur
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (10, 'Antoine Mamady', '775224052', '33 855 12 67', 'sedabsenegal@gmail.com', '33 844 37 33', 'www.thico.sn');

--------- Zone SEDHIOU 
--** Coordonateur
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (11, 'Hassim Kande', '775707251', '33 855 12 67', 'sedabsenegal@gmail.com', '33 844 37 33', 'www.thico.sn');

----Autres
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) VALUES (100, 'Direction Agricole', '33 8763482', '33 955 00 87', 'da@gouv-senegal.sn', '33 844 11 13', 'www.gouv-direction-agricole.sn');

------FIN CONTACT ------
-------------------------------------------------------------------------------------------------------------------------



------------------------------  Personne --------------------------------------------------------------------------------
------Personne---------
-------------------------------------------------------------------------------------------------------------------------
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (0, 'Administrateur', 'Administrateur', '1', 1, 1);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (90, 'Gerant', 'Sedab', '1', 1, 1);


INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (1, 'Thiam', 'Modou', '1', 1, 111);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (50, 'Kande', 'Moulaye', '1', 1, 112);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (51, 'Niang', 'Mamour', '1', 1, 3);

-- Coordonateur zone Nord : Charles Antoine Large
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (52, 'Charles Antoine ', 'Large', '1', 2, 2);

-- Superviseur zone Nord : Charles Antoine Large
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (53, 'Alassane ', 'Kande', '1', 1, 3);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (54, 'Antoine ', 'Diéme', '1', 2, 4);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (55, 'Mbaye ', 'Ndiaye', '1', 2, 5);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (56, 'Jean', 'Ndiaye', '1', 3, 6);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (57, 'Charles', 'Large', '1', 4, 7);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (570, 'Mouhamed', 'Seck', '1', 4, 7);


-- Coordonateur zone SUD : DAHA BALDE 
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (58, 'DAHA ', 'Balde', '1', 5, 8);

-- Coordonateur zone CENTRE EST : VIEUX CISSE 
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (59, 'Vieux ', 'Cisse', '1', 6, 9);

-- Coordonateur zone CENTRE OUEST : ANTOINE MAMADY  
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (60, 'Antoine ', 'Mamady', '1', 7, 10);

-- Coordonateur zone CENTRE OUEST : HASSIM KANDE   
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (61, 'Hasssim ', 'Kande', '1', 8, 11);

--Autres
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (100, 'Ministère de l Agriculture Sénégalais', '', '5', 100, 100);


--Chauffeurs
INSERT INTO pers_personne(pers_id, nom, prenom, civilite, adresse_id , contact_id) VALUES (101, 'Gora Fall', '', '5', 100, 100);
INSERT INTO pers_personne(pers_id, nom, prenom, civilite) VALUES (6, 'MAMADOU WILLANE', '', '5');
INSERT INTO pers_personne(pers_id, nom, prenom, civilite) VALUES (7, 'MAMADOU KASSE', '', '5');
INSERT INTO pers_personne(pers_id, nom, prenom, civilite) VALUES (8, 'MOR FALL', '', '5');
INSERT INTO pers_personne(pers_id, nom, prenom, civilite) VALUES (9, 'ABDOULAYE DIAW', '', '5');






------FIN Personne ------
-------------------------------------------------------------------------------------------------------------------------




------------------------------  Transporteurs ---------------------------------------------------------------------------
------Transporteurs---------
-------------------------------------------------------------------------------------------------------------------------
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (1, 'GORA FALL');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (2, 'SERIGNE FALL');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (3, 'SERIGNE FALL');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (4, ' IBRA FALL');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (5, 'DIEUDONNE SENGH');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (6, 'ALIOUNE KAIRE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (7, 'ALIOUNE SEYE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (8, 'MAMADOU FALL');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (9, 'THIERNO BA');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (10, 'MODOU NDIAYE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (11, 'MODOU CISSE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (12, 'BARRA SARR');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (13, 'ELHADJI NDIAYE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (14, 'ABDOU DIENG');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (15, 'MODOU MBAYE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (16, 'MBARGOU BADIANE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (17, 'MOUSTAPHA NIANE');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (18, 'MOR SECK');
INSERT INTO pgca_transporteur(idtransporteur, libelle) VALUES (19, 'MOUSTAPHA DIOP ');
------FIN Transporteurs ------
-------------------------------------------------------------------------------------------------------------------------

------------------------------  Chauffeurs ------------------------------------------------------------------------------
------Chauffeurs---------
-------------------------------------------------------------------------------------------------------------------------
INSERT INTO pgca_chauffeur(chauff_id, libelle, pers_id, transporteur_idtransporteur) VALUES (1, 'MAMADOU WILLANE', 6, 1);
INSERT INTO pgca_chauffeur(chauff_id, libelle, pers_id, transporteur_idtransporteur) VALUES (2, 'MAMADOU KASSE', 7, 1);
INSERT INTO pgca_chauffeur(chauff_id, libelle, pers_id, transporteur_idtransporteur) VALUES (3, 'ABDOULAYE DIAW', 8, 2);
INSERT INTO pgca_chauffeur(chauff_id, libelle, pers_id, transporteur_idtransporteur) VALUES (4, 'MOR FALL', 9, 2);



------------------------------  Camions ---------------------------------------------------------------------------------
------Camions---------
-------------------------------------------------------------------------------------------------------------------------
INSERT INTO pgca_camion(camion_id, capacitemax, numerocamion, idtransporteur, chauff_id) VALUES (1, '6', 'DK 6754 XS', 1, 1);
INSERT INTO pgca_camion(camion_id, capacitemax, numerocamion, idtransporteur, chauff_id) VALUES (2, '15', 'DK 2040 TB', 1, 1);
INSERT INTO pgca_camion(camion_id, capacitemax, numerocamion, idtransporteur,  chauff_id) VALUES (3, '10', 'FK 8754 VP', 2, 2);
INSERT INTO pgca_camion(camion_id, capacitemax, numerocamion, idtransporteur,  chauff_id) VALUES (4, '7', 'KL 1109 TG', 3, 3);
------FIN CAMIONS ------
-------------------------------------------------------------------------------------------------------------------------

   	


/*
Camion	Chauffeur 	Tel chauffeur	Transporteur	Tel Transporteur
TH 7228 D	MAMADOU WILLANE	Non renseigné	GORA FALL	Non renseigné
DL 6001 D	MAMADOU KASSE	Non renseigné	GORA FALL	Non renseigné
DL 6001 B	MAMADOU KASSE	Non renseigné	GORA FALL	Non renseigné
DL 1873 A	ABDOULAYE DIAW	Non renseigné	GORA FALL	Non renseigné
TH 3932 G	MOR FALL	Non renseigné	GORA FALL	Non renseigné
LG 5686 A	MBAYE SARR	Non renseigné	GORA FALL	Non renseigné
DK 9125 AK	IBRAHIMA KEFI	Non renseigné	GORA FALL	Non renseigné
DL8154D	BABACAR DIOP	Non renseigné	SERIGNE FALL	Non renseigné
DK6871BB	MAGAYE THIAM	Non renseigné	SERIGNE FALL	Non renseigné
TH1482F	IBRAHIMA DIENG	Non renseigné	SERIGNE FALL	Non renseigné
TH 6742 D	PAPE GUEYE	Non renseigné	SERIGNE FALL	Non renseigné
LG 1213 B	GORA GAYE	Non renseigné	SERIGNE FALL	Non renseigné
TH 4265 G	ALIOUNE NDIAYE	Non renseigné	SERIGNE FALL	Non renseigné
KL 9507 B	THIERNO DIENG	Non renseigné	SERIGNE FALL	Non renseigné
TH 2541 F	CHEIKH BEYE	Non renseigné	SERIGNE FALL	Non renseigné
DK 7615 T	MADI CISSE	Non renseigné	SERIGNE FALL	Non renseigné
KL 5677 B	ABIBOU CISSE	Non renseigné	SERIGNE FALL	Non renseigné
DL4849C	MAMBAYE MBAYE	Non renseigné	CHEIKH DIOP	Non renseigné
DL4849 C	MAMBAYE MBAYE	Non renseigné	CHEIKH DIOP	Non renseigné  


FK 4607 A	DJIBY NGOM	Non renseigné	MODOU CISSE	Non renseigné
KL 2384 C	ALIOU MBAYE	Non renseigné	MODOU CISSE	Non renseigné
P
N
KL 2695 C	OMAR SAMB	Non renseigné	MODOU CISSE	Non renseigné
KL 132 V	MODOU KENE	Non renseigné	MODOU CISSE	Non renseigné
KL 0132 V	MODOU KENE	Non renseigné	MODOU CISSE	Non renseigné
KL 1796 C	OMAR MBOW	Non renseigné	MODOU CISSE	Non renseigné
KF 1639 A	DIABEL GUEYE	Non renseigné	MODOU CISSE	Non renseigné

TH 0024 F	ALIOU SARR	Non renseigné		Non renseigné
DL 3053 D	CHEIKH NDIAYE	Non renseigné	BARRA SARR	Non renseigné
DK 4429 AP	MOUHAMADOU KEBE	Non renseigné	BARRA SARR	Non renseigné
DK 7969 AL	MODOU WADE	Non renseigné	BARRA SARR	Non renseigné
DK 9876 AL	ABDOU TINE	Non renseigné	BARRA SARR	Non renseigné
DL 9269 C	ELHADJI LO	Non renseigné		Non renseigné
TH 6590 F	ALIOUNE DIOP	Non renseigné		Non renseigné
TH 5306 F	IBRAHIMA GUEYE	Non renseigné	MODOU MBAYE	Non renseigné
TH 6566 D	ELIMANE NIANE	Non renseigné	MODOU MBAYE	Non renseigné
TH 5638	MOR TALLA	Non renseigné	MODOU MBAYE	Non renseigné
TH 4190 E	MATAR NDIAYE	Non renseigné	MODOU MBAYE	Non renseigné
TH 8035	AHMADOU GUEYE	Non renseigné	MODOU MBAYE	Non renseigné
DK 2448 BE	KHADIM DIAGNE	Non renseigné	MBARGOU BADIANE	Non renseigné
DK 2848 BE	KHADIM DIAGNE	Non renseigné	MBARGOU BADIANE	Non renseigné
KD 5055 A	MASSAER NDAO	Non renseigné	MOUSTAPHA NIANE	Non renseigné
DK 4075 B	PAPE MOU	Non renseigné		Non renseigné
TH 6187 A	MODOU CISSE	Non renseigné	MOR SECK	Non renseigné
ZG 4999 B	ABDOU SAKOR	Non renseigné	MODOU DRAME	Non renseigné
TH 5619 G	IBRAHIMA NDIAYE	Non renseigné		Non renseigné
DL 5359 C	ABDOU SENE	Non renseigné	MOUSTAPHA DIOP	Non renseigné
DL 0089 D	ALIOU TOURE	Non renseigné	MOUSTAPHA DIOP	Non renseigné
*/

------FIN Chauffeurs ------
-------------------------------------------------------------------------------------------------------------------------


------------------------------  CLIENTS SEDAB ------------------------------------------------------------------------------
------CLIENTS ZONE NORD---------   
-------------------------------------------------------------------------------------------------------------------------


INSERT INTO pgca_client(cli_id, libelle) VALUES (1, 'GIE PELLITAL AERE LAO');
INSERT INTO pgca_client(cli_id, libelle) VALUES (2, 'CNT IBEU SALL THIAGAR');
INSERT INTO pgca_client(cli_id, libelle) VALUES (3, 'GIE NAXADY DERETE CHEIKH D');
INSERT INTO pgca_client(cli_id, libelle) VALUES (4, 'UNION LOCALE RONKH');
INSERT INTO pgca_client(cli_id, libelle) VALUES (5, 'UNION LOCALE DIAWAR');
INSERT INTO pgca_client(cli_id, libelle) VALUES (6, 'UNION LOCALE WASSOUL');
INSERT INTO pgca_client(cli_id, libelle) VALUES (7, 'UNION LOCALE MBOUNDOUM');
INSERT INTO pgca_client(cli_id, libelle) VALUES (8, 'UNION LOCALE KHEUN');
INSERT INTO pgca_client(cli_id, libelle) VALUES (9, 'UNION DES GIE GUEDE CHAN');
INSERT INTO pgca_client(cli_id, libelle) VALUES (10, 'UNION DES GIES KASSACK NO');
INSERT INTO pgca_client(cli_id, libelle) VALUES (11, 'UNION DES GIES DE MBAGAM');
INSERT INTO pgca_client(cli_id, libelle) VALUES (12, 'UNION DEBY TIGUETTE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (13, 'UNION DES GIES DE KASSACK');
INSERT INTO pgca_client(cli_id, libelle) VALUES (14, 'ABDOU KHADRE NIANG');
INSERT INTO pgca_client(cli_id, libelle) VALUES (15, 'DEPOT DE THIEWLE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (16, 'MBAYE NDIAYE RICHARD TOLL');
INSERT INTO pgca_client(cli_id, libelle) VALUES (17, 'DAGANA');
INSERT INTO pgca_client(cli_id, libelle) VALUES (18, 'ROSSE BETHIO');
INSERT INTO pgca_client(cli_id, libelle) VALUES (19, 'GUIA DOYANE SILEYE BOCAR');
INSERT INTO pgca_client(cli_id, libelle) VALUES (20, 'DEPOT DE NDIOUM');
INSERT INTO pgca_client(cli_id, libelle) VALUES (21, 'DEPOT SAED NHIANGA');
INSERT INTO pgca_client(cli_id, libelle) VALUES (22, 'COMMUNE DE PODOR');
INSERT INTO pgca_client(cli_id, libelle) VALUES (23, 'NDIOUGUE MERESSE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (24, 'DEPOT DE TAREDJI');
INSERT INTO pgca_client(cli_id, libelle) VALUES (25, 'GNITH');
INSERT INTO pgca_client(cli_id, libelle) VALUES (26, 'MBANE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (27, 'USAGERS DE MAKA DIAMA');
INSERT INTO pgca_client(cli_id, libelle) VALUES (28, 'NADIEL');
INSERT INTO pgca_client(cli_id, libelle) VALUES (29, 'ADAMA DIENG');
INSERT INTO pgca_client(cli_id, libelle) VALUES (30, 'COMMISSION THIAGAR');
INSERT INTO pgca_client(cli_id, libelle) VALUES (31, 'MEDINA DIATBE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (32, 'MAGASIN PETE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (33, 'DIOMANDOU');
INSERT INTO pgca_client(cli_id, libelle) VALUES (34, 'MBAYE THIAM');
INSERT INTO pgca_client(cli_id, libelle) VALUES (35, 'GAMOU THILLER');
INSERT INTO pgca_client(cli_id, libelle) VALUES (36, 'GAYA');
INSERT INTO pgca_client(cli_id, libelle) VALUES (37, 'BOKHOL');
INSERT INTO pgca_client(cli_id, libelle) VALUES (38, 'GIE NATANGUE OUSSEYNOU');
INSERT INTO pgca_client(cli_id, libelle) VALUES (39, 'GIE FALL ET FRERE ABDA FALL');
INSERT INTO pgca_client(cli_id, libelle) VALUES (40, 'CHEIKH DIAW');
INSERT INTO pgca_client(cli_id, libelle) VALUES (41, 'DAOUDA GAYE');
INSERT INTO pgca_client(cli_id, libelle) VALUES (42, 'MADIOP');
INSERT INTO pgca_client(cli_id, libelle) VALUES (43, 'BABA DIALLO');
INSERT INTO pgca_client(cli_id, libelle) VALUES (44, 'AMADOU THIAM AERE LAO');
INSERT INTO pgca_client(cli_id, libelle) VALUES (45, 'NDIANDANE');


------FIN CLIENTS ------
-------------------------------------------------------------------------------------------------------------------------




-------------------------------------------- Stock ---------------------------------------------------------------------
------Stock---------
-------------------------------------------------------------------------------------------------------------------------  
--Siège
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (0,  'Siège Sedab');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (100,  'Stock Etat');
-- Superviseur Zone Nord
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (1,  'Zone Nord');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (2,  'Rufisque');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (3,  'Sanghalkam');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (4,  'Kebemer');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (8,  'Thiagar');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (9,  'Richard toll');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (10,  'Matam');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (11,  'Diourbel');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (12,  'Kougheul');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (13,  'Sibassor');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (14,  'Kounkane');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (15,  'Kolda Central');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (16,  'Magasin Kolda sare kemo');
INSERT INTO pgca_stock(stock_id, libelle)  VALUES (17,  'Magasin Kolda CTS');



INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (1, 'Rufisque',  1, 1, 90, 2 , 41 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (2, 'Sanghalkam',   1, 1, 90, 3 , 48 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (3, 'Kebemer',  1, 1, 90, 4 , 284 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (4, 'Thiagar',  1, 1, 90, 8 , 356 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (5, 'Richard toll',   1, 1, 90, 9 , 349 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (6, 'Saed Matam',   1, 1, 90, 10 , 322 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (7, 'Diourbel',   1, 1, 90, 11 , 66 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (8, 'Kougheul',   1, 1, 90, 12 , 151 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (9, 'Sibassor',   1, 1, 90, 13 ,  171 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (10, 'Kounkane',  1, 1, 90, 14 , 254 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (11, 'Kolda Central',  1, 1, 90, 15 , 277 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (12, 'Kolda sare kemo',  1, 1, 90, 16 , 241 , 0);
INSERT INTO pgca_pointdevente(ptv_id, libelle,  dernier_contact_id, derniere_adressepostale_id, pers_id, stock_id , commune_id , typeMag)  VALUES (13, 'Kolda CTS',   1, 1, 90, 17 , 232 , 0);



------FIN PV ------
-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------- Point de Collecte ----------------------------------------------------------
------PC---------
-------------------------------------------------------------------------------------------------------------------------  
INSERT INTO pgca_pointdecollecte(pdc_id, libelle, superficie, adrs_id,  cont_id, pers_id,  stock_id)  VALUES (0,  'Siège Sedab', 700, 1, 1, 1,  0);
--INSERT INTO pgca_pointdecollecte(pdc_id, libelle, superficie, adrs_id,  cont_id, pers_id,  stock_id)  VALUES (1,  'Point de Collecte de Kolda', 700, 2,  2, 2,  5);
--INSERT INTO pgca_pointdecollecte(pdc_id, libelle, superficie, adrs_id,  cont_id, pers_id,  stock_id)  VALUES (2,  'Point de Collecte de Tamba', 700, 2, 6, 6,  6);
--INSERT INTO pgca_pointdecollecte(pdc_id, libelle, superficie, adrs_id,  cont_id, pers_id,  stock_id)  VALUES (3,  'Point de Collecte de Kaloack', 700, 2, 5, 10, 7);


------FIN PC ------
-------------------------------------------------------------------------------------------------------------------------
     		


----------------------------------------------- PROFIL ------------------------------------------------------------------
------PROFIL ------
-------------------------------------------------------------------------------------------------------------------------  
insert into prfl_profil (prfl_id, code, libelle) values (1, 'admin','Administrateur');
insert into prfl_profil (prfl_id, code, libelle) values (2, 'manager','Manager');
insert into prfl_profil (prfl_id, code, libelle) values (3, 'agentsaisie','Agent de saisie');  
--insert into prfl_profil (prfl_id, code, libelle) values (4, 'agentcollecte','Gerant des points de vente (Superviseur) ');
insert into prfl_profil (prfl_id, code, libelle) values (5, 'magasinier','Superviseur Magasin'); 
--insert into prfl_profil (prfl_id, code, libelle) values (6, 'drdr','Direction Regional'); 

------FIN PROFIL ------
-------------------------------------------------------------------------------------------------------------------------


----------------------------------------------- THEMES ------------------------------------------------------------
------THEMES ------
-------------------------------------------------------------------------------------------------------------------------
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (1, '#80c347', 'nobg' , 'standard');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (2, '#80c347', 'themeGreenBG', 'Green');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (3, '#49b97e', 'nobg' , 'olive');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (4, '#80c347', 'nobg' , 'blue');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (5, '#80c347', 'nobg' , 'black');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (6, '#80c347', 'nobg' , 'yellow');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (7, '#80c347', 'nobg' , 'pink');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (8, '#80c347', 'nobg' ,  'green');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (9, '#80c347', 'themeGreenBG2' , 'green2');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (10,'#80c347', 'themeGreenBG3' ,  'green3');
INSERT INTO usertheme( idtheme, themecolor, themeheader, themename) VALUES (11,'#80c347', 'themeGreenLIGHT' ,  'green3');


 
----------------------------------------------- UTILISATEURS ------------------------------------------------------------
------UTILISATEURS ------
-------------------------------------------------------------------------------------------------------------------------
---INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier, idTheme , nb_connexion) VALUES (1, 'manager', '93E44B157897899D47E69A387B01AF82', 'true',  0, 1, 2, 'false', 1 , 0);
INSERT INTO user_utilisateur(user_id, email, codeutilisateur,  motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier ,  idTheme , nb_connexion) VALUES (2, 'mamourniang@sedabsenegal.com', 'mamourniang@sedabsenegal.com', '93E44B157897899D47E69A387B01AF82',  'true',  0, 0, 1,'false', 1 , 0);
INSERT INTO user_utilisateur(user_id, email, codeutilisateur,  motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier, idTheme , nb_connexion) VALUES (33, 'modouthiam@sedabsenegal.com',  'modouthiam@sedabsenegal.com',   '93E44B157897899D47E69A387B01AF82', 'true', 0, 1, 2, 'false', 2 , 0);
INSERT INTO user_utilisateur(user_id, email, codeutilisateur,  motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier, idTheme , nb_connexion) VALUES (44, 'moulayekande@sedabsenegal.com', 'modouthiam@sedabsenegal.com',  '93E44B157897899D47E69A387B01AF82', 'true',  0, 50, 2, 'false', 2 , 0);

--- Agent de saisie
INSERT INTO user_utilisateur(user_id, email, codeutilisateur,  motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier,  idTheme , nb_connexion) VALUES (3, 'astougueye@sedabsenegal.com', 'modouthiam@sedabsenegal.com',  '93E44B157897899D47E69A387B01AF82',     'true',  0, 51, 3,'false' , 1 , 0);
--INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (55, 'mniang', '93E44B157897899D47E69A387B01AF82',     'true',  0, 51, 3,'false' , 1);
--INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (66, 'mdiop', '93E44B157897899D47E69A387B01AF82',     'true',  0, 13, 3,'false' , 1);

--- Magasinier 
INSERT INTO user_utilisateur(user_id, email, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme , nb_connexion) 
VALUES (80, 'charleslarge@sedabsenegal.com',  'charleslarge@sedabsenegal.com',   '93E44B157897899D47E69A387B01AF82',    'true', 1, 57, 5,'false', 1 , 0);

--- Superviseur
INSERT INTO user_utilisateur(user_id, email, codeutilisateur,  motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme , nb_connexion) 
VALUES (8, 'mouhamedseck@sedabsenegal.com',  'mouhamedseck@sedabsenegal.com',  '93E44B157897899D47E69A387B01AF82',    'true', 1, 570, 4,'false', 1 , 0);


/*
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (8, 'magasinierRufisque', '93E44B157897899D47E69A387B01AF82',    'true', 1, 12, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (9, 'magasinierSanghalkam', '93E44B157897899D47E69A387B01AF82',  'true', 2, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (56, 'magasinierKebemer', '93E44B157897899D47E69A387B01AF82',    'true', 3, 5, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (57, 'magasinierThiagar', '93E44B157897899D47E69A387B01AF82',    'true', 4, 12, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (58, 'magasinierRichardToll', '93E44B157897899D47E69A387B01AF82',  'true', 5, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (59, 'magasinierSaedMatam', '93E44B157897899D47E69A387B01AF82',  'true', 6, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (60, 'magasinierDiourbel', '93E44B157897899D47E69A387B01AF82',  'true', 7, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (61, 'magasinierKougheul', '93E44B157897899D47E69A387B01AF82',  'true', 8, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (62, 'magasinierSibassor', '93E44B157897899D47E69A387B01AF82',  'true', 9, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (63, 'magasinierKounkane', '93E44B157897899D47E69A387B01AF82',  'true', 10, 10, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (7, 'magasinierKolda', '93E44B157897899D47E69A387B01AF82',    'true', 11, 5, 5,'false', 1);
INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (64, 'magasinierKoldaSareKemo', '93E44B157897899D47E69A387B01AF82',  'true', 12, 10, 5,'false', 1);
*/

--- Agent de collecte
--INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (4, 'agentcollecteKolda', '93E44B157897899D47E69A387B01AF82',  'true',  1, 5, 4,'false' , 1);
--INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (5, 'agentcollecteTamba', '93E44B157897899D47E69A387B01AF82',  'true',  2, 12, 4,'false', 1);
--INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, pdc_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (6, 'agentcollecteKoalack', '93E44B157897899D47E69A387B01AF82', 'true', 3, 10, 4,'false', 1);



--  DRDR Kolda
--INSERT INTO user_utilisateur(user_id, codeutilisateur, motdepasse, est_valide, ptv_id, pers_id, prfl_id, mdpamodifier,  idTheme) VALUES (70, 'drdr', '93E44B157897899D47E69A387B01AF82',  'true', 3, 10, 6,'false', 1);






