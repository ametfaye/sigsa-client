-- Sequence: hibernate_sequence

-- DROP SEQUENCE hibernate_sequence;

CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1000
  MAXVALUE 9223372036854775807
  START 1000
  CACHE 1;
ALTER TABLE hibernate_sequence  OWNER TO sigsa;

  
  
   -- PAYS
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (1, 'Sénégal','SN', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (2, 'Mali','ML', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (3, 'Côte d''ivoire','CI', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (4, 'Cameroun','CM', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (5, 'Guinée','GU', true, 'F Guinéen');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (6, 'Burkina faso','BF', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada) values (7, 'Mauritanie','MR', false);
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (8, 'Niger','NG', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada) values (9, 'Ghana','GH', false);
insert into pays (pays_id, libelle, code_pays, est_espace_ohada) values (10, 'Nigéria','NI', false);
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (11, 'Togo','TG', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (12, 'Bénin','BJ', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (13, 'Centrafrique','RCA', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (14, 'Comores','CO', true, 'FC');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (15, 'Congo','CG', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (16, 'Gabon','GA', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (17, 'Guinée-Bissau','GW', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (18, 'Guinée Equatoriale','GEQ', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (19, 'RD Congo','CD', true, 'F CFA');
insert into pays (pays_id, libelle, code_pays, est_espace_ohada, code_monetaire) values (20, 'Tchad','TCD', true, 'F CFA');

-- REGION
insert into regn_region (regn_id, libelle, pays_id, code_region) values (1, 'Dakar', 1, 'DKR');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (2, 'Diourbel', 1, 'DRB');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (3, 'Fatick', 1, 'FTK');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (4, 'Kaffrine', 1, 'KFN');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (5, 'Kaolack', 1, 'KLK');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (6, 'Kolda', 1, 'KLD');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (7, 'Kédougou', 1, 'KEG');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (8, 'Louga', 1, 'LGA');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (9, 'Matam', 1, 'MTM');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (10, 'Saint-louis', 1, 'STL');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (11, 'Sédhiou', 1, 'SED');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (12, 'Tambacounda', 1, 'TMB');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (13, 'Thies',1, 'THS');
insert into regn_region (regn_id, libelle, pays_id, code_region) values (14, 'Ziguinchor',1, 'ZIG');



------------------ Départements------------------------

--Départements de DAKAR
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (1, 'DK01', 'DAKAR', 1);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (2, 'DK02', 'GUEDIAWAYE', 1);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (3, 'DK03', 'PIKINE', 1);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (4, 'DK04', 'RUFISQUE', 1);

--Départements de DIOURBEL
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (5, 'DL01', 'BAMBEY', 2);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (6, 'DL02', 'DIOURBEL', 2);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (7, 'DL03', 'MBACKE', 2);

--Départements de FATICK
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (8, 'FK01', 'FATICK', 3);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (9, 'FK02', 'FOUNDIOUGNE', 3);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (10, 'FK03', 'GOSSAS', 3);

--Départements de KAFFRINE
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (11, 'KF01', 'KAFFRINE', 4);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (12, 'KF02', 'MBIRKILANE', 4);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (13, 'KF03', 'KOUNGHEUL', 4);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (14, 'KF04', 'MALEM HODAR', 4);

--Départements de KAOLACK
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (15, 'KL01', 'KAOLACK', 5);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (16, 'KL02', 'NIORO', 5);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (17, 'KL03', 'GUINEGUINEO', 5);

--Départements de KOLDA
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (18, 'KG01', 'KEDOUGOU', 7);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (19, 'KG02', 'SALEMATA', 7);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (20, 'KG03', 'SARAYA', 7);

--Départements de  KEDOUGOU 
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (21, 'KD01', 'KOLDA', 6);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (22, 'KD02', 'MEDINA YORO FOULAH', 6);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (23, 'KD03', 'VELINGARA', 6);

--Départements de LOUGA
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (24, 'LG01', 'LOUGA', 8);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (25, 'LG02', 'KEBEMER', 8);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (26, 'LG03', 'LINGUERE', 8);

--Départements de MATAM
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (27, 'MT01', 'MATAM', 9);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (28, 'MT02', 'KANEL', 9);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (29, 'MT03', 'RANEROU FERLO', 9);

--Départements de SAINT-LOUIS
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (30, 'SL03', 'DAGANA', 10);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (31, 'SL02', 'PODOR', 10);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (32, 'SL01', 'SAINT-LOUIS', 10);

--Départements de SEDHIOU
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (33, 'SD03', 'BOUNKILING', 11);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (34, 'SD02', 'GOUDOMP', 11);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (35, 'SD01', 'SEDHIOU', 11);

--Départements de TAMBACOUNDA
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (36, 'TC02', 'BAKEL', 12);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (37, 'TC03', 'GOUDIRY', 12);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (38, 'TC04', 'KOUPENTOUM', 12);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (39, 'TC01', 'TAMBACOUNDA', 12);

--Départements de THIES
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (40, 'TH03', 'MBOUR', 13);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (41, 'TH01', 'THIES', 13);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (42, 'TH02', 'TIVAOUANE', 13);

--Départements de ZIGUINCHOR
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (43, 'ZG03', 'BIGNONA', 14);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (44, 'ZG02', 'OUSSOUYE', 14);
INSERT INTO departement(departement_id, code, libelle, regn_id) VALUES (45, 'ZG01', 'ZIGUINCHOR', 14);

------------------ Départements ------------------------
















------------------ Communes ------------------------

--Commune - DPT DAKAR
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1, 'DK01101', 'GOREE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (2, 'DK01102', 'DAKAR – PLATEAU', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (3, 'DK01103', 'GUEULE TAPEE - FASS - COLOBANE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (4, 'DK01104', 'FANN - POINT E - AMITIE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (5, 'DK01105', 'MEDINA', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (6, 'DK01201', 'GRAND DAKAR', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (7, 'DK01202', 'BISCUITERIE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (8, 'DK01203', 'DIEUPPEUL - DERKLE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (9, 'DK01204', 'HANN - BEL AIR', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (10, 'DK01205', 'SICAP - LIBERTE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (11, 'DK01206', 'H.L.M', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (12, 'DK01301', 'MERMOZ - SACRE CŒUR', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (13, 'DK01302', 'OUAKAM', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (14, 'DK01303', 'NGOR', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (15, 'DK01304', 'YOFF', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (16, 'DK01401', 'GRAND YOFF', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (17, 'DK01402', 'PATTE D''OIE', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (18, 'DK01403', 'PARCELLES ASSAINIES', 1);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (19, 'DK01404', 'CAMBERENE', 1);

--Commune - DPT GUEDIAWAYE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (20, 'DK02101', 'GOLF SUD', 2);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (21, 'DK02003', 'SAM NOTAIRE', 2);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (22, 'DK02004', 'NDIAREME LIMAMOULAYE', 2);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (23, 'DK02005', 'MEDINA GOUNASS', 2);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (24, 'DK02006', 'WAKHINANE NIMZATH', 2);

--Commune - DPT PIKINE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (25, 'DK03101', 'THIAROYE GARE', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (26, 'DK03102', 'MBAO', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (27, 'DK03103', 'THIAROYE SUR MER', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (28, 'DK03104', 'TIVAOUANE DIACKSAO', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (29, 'DK03105', 'DIAMAGUEUNE SICAP MBAO', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (30, 'DK03201', 'KEUR MASSAR', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (31, 'DK03202', 'MALIKA', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (32, 'DK03203', 'YEUMBEUL SUD', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (33, 'DK03204', 'YEUMBEUL NORD', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (34, 'DK03301', 'PIKINE OUEST', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (35, 'DK03302', 'PIKINE EST', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (36, 'DK03303', 'PIKINE NORD', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (37, 'DK03304', 'DALIFORT', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (38, 'DK03305', 'DJIDAH THIAROYE KAO', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (39, 'DK03306', 'GUINAW RAIL NORD', 3);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (40, 'DK03307', 'GUINAW RAIL SUD', 3);

--Commune - DPT RUFISQUE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (41, 'DK04001', 'RUFISQUE (VILLE)', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (42, 'DK04002', 'RUFISQUE EST', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (43, 'DK04003', 'RUFISQUE NORD', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (44, 'DK04004', 'RUFISQUE OUEST', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (45, 'DK04101', 'BARGNY', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (46, 'DK04102', 'DIAMNIADIO', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (47, 'DK04103', 'SEBIKOTANE', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (48, 'DK04104', 'SANGALKAM', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (49, 'DK04105', 'JAXAAY-PARCELLES-NIACOUL RAB', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (50, 'DK04106', 'SENDOU', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (51, 'DK04107', 'BAMBILOR', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (52, 'DK04108', 'YENN', 4);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (53, 'DK04109', 'TIVAOUANE-PEUL-NIAGA', 4);

--Commune - DPT BAMBEY
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (54, 'DL01001', 'BAMBEY', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (55, 'DL01101', 'DINGUIRAYE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (56, 'DL01102', 'BABA GARAGE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (57, 'DL01103', 'KEUR SAMBA KANE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (58, 'DL01201', 'NGOYE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (59, 'DL01202', 'THIAKHAR', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (60, 'DL01203', 'NDONDOL', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (61, 'DL01204', 'NDANGALMA', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (62, 'DL01301', 'LAMBAYE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (63, 'DL01302', 'REFANE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (64, 'DL01303', 'GAWANE', 5);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (65, 'DL01304', 'NGOGOM', 5);

--Commune - DPT DIOURBEL
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (66, 'DL02001', 'DIOURBEL', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (67, 'DL02101', 'NDOULO', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (68, 'DL02102', 'NGOHE', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (69, 'DL02103', 'PATTAR', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (70, 'DL02104', 'TOCKY GARE', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (71, 'DL02105', 'TOURE MBONDE', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (72, 'DL02201', 'NDANKH SENE', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (73, 'DL02202', 'GADE ESCALE', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (74, 'DL02203', 'TOUBA LAPPE', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (75, 'DL02204', 'KEUR NGALGOU', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (76, 'DL02205', 'NDINDY', 6);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (77, 'DL02206', 'TAIBA MOUSTAPHA', 6);

--Commune - DPT MBACKE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (78, 'DL03001', 'MBACKE', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (79, 'DL03101', 'TOUBA MOSQUEE', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (80, 'DL03102', 'DALLA NGABOU', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (81, 'DL03103', 'MISSIRAH', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (82, 'DL03104', 'NGHAYE', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (83, 'DL03105', 'TOUBA FALL', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (84, 'DL03201', 'DAROU SALAM', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (85, 'DL03202', 'DAROU NAHIM', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (86, 'DL03203', 'KAEL', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (87, 'DL03204', 'MADINA', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (88, 'DL03205', 'TOUBA MBOUL', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (89, 'DL03206', 'TAÏBA THIEKENE', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (90, 'DL03207', 'DENDEYE GOUY GUI', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (91, 'DL03208', 'NDIOUMANE TAÏBA THIEKENE', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (92, 'DL03301', 'TAÏF', 7);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (93, 'DL03302', 'SADIO', 7);

--Commune - DPT FATICK
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (94, 'FK01001', 'FATICK', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (95, 'FK01002', 'DIOFFIOR', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (96, 'FK01003', 'DIAKHAO', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (97, 'FK01004', 'THIARE NDIALGUI', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (98, 'FK01005', 'DIAOULE', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (99, 'FK01006', 'MBELLACADIAO', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (100, 'FK01007', 'NDIOP', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (101, 'FK01008', 'DJILASSE', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (102, 'FK01009', 'FIMELA', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (103, 'FK01101', 'LOUL SESSENE', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (104, 'FK01102', 'PALMARIN FACAO', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (105, 'FK01103', 'NIAKHAR', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (106, 'FK01104', 'NGAYOKHEME', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (107, 'FK01105', 'PATAR', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (108, 'FK01106', 'DIARRERE', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (109, 'FK01107', 'DIOUROUP', 8);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (110, 'FK01108', 'TATTAGUINE', 8);

--Commune - DPT FOUNDIOUGNE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (111, 'FK02001', 'FOUNDIOUGNE', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (112, 'FK02002', 'SOKONE', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (113, 'FK02003', 'SOUM', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (114, 'FK02004', 'KARANG POSTE', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (115, 'FK02101', 'KEUR SALOUM DIANE', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (116, 'FK02102', 'KEUR SAMBA GUEYE', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (117, 'FK02103', 'TOUBACOUTA', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (118, 'FK02104', 'NIORO ALASSANE TALL', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (119, 'FK02201', 'DIOSSONG', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (120, 'FK02202', 'DJILOR', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (121, 'FK02203', 'NIASSENE', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (122, 'FK02204', 'DIAGANE BARKA', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (123, 'FK02205', 'MBAM', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (124, 'FK02301', 'PASSY', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (125, 'FK02302', 'BASSOUL', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (126, 'FK02303', 'DIONEWAR', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (127, 'FK02304', 'DJIRNDA', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1270, 'FK12704', 'KARANG', 9);


--Commune - DPT GOSSAS
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (128, 'FK03001', 'GOSSAS', 10);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (129, 'FK03101', 'COLOBANE', 10);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (130, 'FK03102', 'MBAR', 10);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (131, 'FK03201', 'NDIENE LAGANE', 10);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (132, 'FK03202', 'OUADIOUR', 10);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (133, 'FK03203', 'PATAR LIA', 10);

--Commune - DPT KAFFRINE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (134, 'KF01001', 'KAFFRINE', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (135, 'KF01002', 'NGANDA', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (136, 'KF01003', 'DIAMAGADIO', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (137, 'KF01004', 'DIOKOUL BELBOUCK', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (138, 'KF01101', 'KATHIOTTE', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (139, 'KF01102', 'MEDINATOUL SALAM', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (140, 'KF01103', 'GNIBY', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (141, 'KF01104', 'BOULEL', 11);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (142, 'KF01105', 'KAHI', 11);

--Commune - DPT MBIRKILANE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (143, 'KF02001', 'BIRKELANE', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (144, 'KF02002', 'KEUR MBOUCKI', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (145, 'KF02003', 'TOUBA MBELLA', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (146, 'KF02004', 'DIAMAL', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (147, 'KF02101', 'MABO', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (148, 'KF02102', 'NDIOGNICK', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (149, 'KF02103', 'SEGRE-GATTA', 12);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (150, 'KF02104', 'MBEULEUP', 12);

--Commune - DPT KOUNGHEUL
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (151, 'KF03001', 'KOUNGHEUL', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (152, 'KF03101', 'MISSIRAH WADENE', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (153, 'KF03102', 'MAKA YOP', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (154, 'KF03103', 'NGAINTHE PATHE', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (155, 'KF03201', 'FASS THIEKENE', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (156, 'KF03202', 'SALY ESCALE', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (157, 'KF03203', 'IDA MOURIDE', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (158, 'KF03301', 'RIBOT ESCALE', 13);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (159, 'KF03302', 'LOUR ESCALE', 13);

--Commune - DPT MALEM HODAR
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (160, 'KF04001', 'MALEM HODDAR', 14);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (161, 'KF04101', 'DAROU MINAME', 14);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (162, 'KF04102', 'KHELCOM', 14);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (163, 'KF04103', 'NDIOUM NGAINTH', 14);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (164, 'KF04104', 'NDIOBENE SAMBA LAMO', 14);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (165, 'KF04201', 'SAGNA', 14);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (166, 'KF04202', 'DIANKE SOUF', 14);

--Commune - DPT KAOLACK
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (167, 'KL01001', 'KAOLACK', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (168, 'KL01002', 'KAHONE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (169, 'KL01003', 'NDOFFANE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (170, 'KL01101', 'GANDIAYE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (171, 'KL01102', 'SIBASSOR', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (172, 'KL01103', 'KEUR BAKA', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (173, 'KL01104', 'LATMINGUE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (174, 'KL01201', 'THIARE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (175, 'KL01202', 'KEUR SOCE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (176, 'KL01203', 'NDIAFFATE', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (177, 'KL01204', 'NDIEDIENG', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (178, 'KL01205', 'DYA', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (179, 'KL01206', 'NDIEBEL', 15);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (180, 'KL01207', 'THIOMBY', 15);

--Commune - DPT NIORO
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (181, 'KL02001', 'NIORO', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (182, 'KL02101', 'KEUR MADIABEL', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (183, 'KL02102', 'KAYEMOR', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (184, 'KL02103', 'MEDINA SABAKH', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (185, 'KL02104', 'NGAYENE', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (186, 'KL02105', 'GAINTHE KAYE', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (187, 'KL02201', 'DABALY', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (188, 'KL02202', 'DAROU SALAM', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (189, 'KL02203', 'PAOS KOTO', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (190, 'KL02204', 'POROKHANE', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (191, 'KL02101', 'TAÏBA NIASSENE', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (192, 'KL02102', 'KEUR MABA DIAKHOU', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (193, 'KL02103', 'KEUR MADONGO', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (194, 'KL02104', 'NDRAME ESCALE', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (195, 'KL02105', 'WACK NGOUNA', 16);
--Add manuel custom
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1950, 'KL1950', 'Ndieghenne Ndiba', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1951, 'KL1951', 'Thiare Alassane', 16);


--Commune - DPT GUINEGUINEO
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (196, 'KL03001', 'GUINEGUINEO', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (197, 'KL03101', 'FASS', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (198, 'KL03102', 'MBOSS', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (199, 'KL03103', 'KHELCOM – BIRANE', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (200, 'KL03104', 'MBADAKHOUNE', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (201, 'KL03201', 'NDIAGO', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (202, 'KL03202', 'NGATHIE NAOUDE', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (203, 'KL03203', 'GAGNICK', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (204, 'KL03204', 'DARA MBOSS', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (205, 'KL03301', 'NGUELOU', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (206, 'KL03302', 'OUROUR', 17);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (207, 'KL03303', 'PANAL OUOLOF', 17);

--Commune - DPT KEDOUGOU
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (208, 'KG01001', 'KEDOUGOU', 18);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (209, 'KG01101', 'NINEFECHA', 18);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (210, 'KG01102', 'BANDAFASSI', 18);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (211, 'KG01103', 'TOMBORONCOTO', 18);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (212, 'KG01104', 'DINDEFELO', 18);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (213, 'KG01201', 'FONGOLIMBI', 18);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (214, 'KG01202', 'DIMBOLI', 18);

--Commune - DPT SALEMATA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (215, 'KG02001', 'SALEMATA', 19);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (216, 'KG02101', 'KEVOYE', 19);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (217, 'KG02102', 'DAKATELI', 19);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (218, 'KG02201', 'ETHIOLO', 19);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (219, 'KG02202', 'OUBADJI', 19);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (220, 'KG02203', 'DAROU SALAM', 19);

--Commune - DPT SARAYA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (221, 'KG03001', 'SARAYA', 20);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (222, 'KG03101', 'BEMBOU', 20);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (223, 'KG03102', 'MEDINA BAFFE', 20);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (224, 'KG03201', 'SABADOLA', 20);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (225, 'KG03202', 'KHOSSANTO', 20);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (226, 'KG03203', 'MISSIRAH SIRIMANA', 20);

--Commune - DPT KOLDA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (227, 'KD01001', 'KOLDA', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (228, 'KD01002', 'DIALAMBERE', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (229, 'KD01003', 'MEDINA CHERIF', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (230, 'KD01004', 'MAMPATIM', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (231, 'KD01101', 'BAGADADJI', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (232, 'KD01102', 'COUMBACARA', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (233, 'KD01103', 'DABO', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (234, 'KD01104', 'THIETTY', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (235, 'KD01105', 'SARE BIDJI', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (236, 'KD01201', 'GUIRO YERO BOCAR', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (237, 'KD01202', 'DIOULACOLON', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (238, 'KD01203', 'TANKANTO ESCALE', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (239, 'KD01301', 'MEDINA EL HADJ', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (240, 'KD01302', 'SALIKEGNE', 21);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (241, 'KD01303', 'SARE YOBA DIEGA', 21);

--Commune - DPT MEDINA YORO FOULAH
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (242, 'KD02001', 'MEDINA YORO FOULAH', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (243, 'KD02002', 'PATA', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (244, 'KD02101', 'BADION', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (245, 'KD02102', 'FAFACOUROU', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (246, 'KD02201', 'BOUROUCO', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (247, 'KD02202', 'BIGNARABE', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (248, 'KD02203', 'NDORNA', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (249, 'KD02204', 'KOULINTO', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (250, 'KD02301', 'NIAMING', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (251, 'KD02302', 'DINGUIRAYE', 22);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (252, 'KD02303', 'KEREWANE', 22);

--Commune - DPT VELINGARA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (253, 'KD03001', 'VELINGARA', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (254, 'KD03002', 'KOUNKANE', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (255, 'KD03003', 'DIAOBE-KABENDOU', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (256, 'KD03101', 'KANDIAYE', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (257, 'KD03102', 'SARE COLY SALLE', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (258, 'KD03103', 'KANDIA', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (259, 'KD03104', 'NEMATABA', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (260, 'KD03201', 'PAKOUR', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (261, 'KD03202', 'PAROUMBA', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (262, 'KD03203', 'OUASSADOU', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (263, 'KD03301', 'BONCONTO', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (264, 'KD03302', 'LINKERING', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (265, 'KD03303', 'MEDINA GOUNASS', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (266, 'KD03304', 'SINTHIANG KOUNDARA', 23);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (2666, 'KD0330444', 'ANAMBE', 23);


--Commune - DPT LOUGA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (267, 'LG01001', 'LOUGA', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (268, 'LG01002', 'NDIAGNE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (269, 'LG01101', 'COKI', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (270, 'LG01102', 'GUET ARDO', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (271, 'LG01103', 'THIAMENE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (272, 'LG01104', 'PETE OUARACK', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (273, 'LG01105', 'KEUR MOMAR SARR', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (274, 'LG01201', 'NGUER MALAL', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (275, 'LG01202', 'SYER', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (276, 'LG01203', 'GANDE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (277, 'LG01204', 'MBEDIENE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (278, 'LG01205', 'NIOMRE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (279, 'LG01206', 'NGUIDILE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (280, 'LG01207', 'KELE GUEYE', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (281, 'LG01301', 'LEONA', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (282, 'LG01302', 'NGUEUNE SARR', 24);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (283, 'LG01303', 'SAKAL', 24);

--Commune - DPT KEBEMER
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (284, 'LG02001', 'KEBEMER', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (285, 'LG02002', 'GUEOUL', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (286, 'LG02003', 'BANDEGNE OUOLOF', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (287, 'LG02004', 'DIOKOUL DIAWRIGNE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (288, 'LG02101', 'KAB GAYE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (289, 'LG02102', 'NDANDE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (290, 'LG02103', 'THIEPPE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (291, 'LG02104', 'MBACKE CAJOR', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (292, 'LG02105', 'DAROU MARNANE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (293, 'LG02201', 'DAROU MOUSTY', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (294, 'LG02202', 'MBADIANE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (295, 'LG02203', 'NDOYENE', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (296, 'LG02204', 'SAM YABAL', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (297, 'LG02205', 'TOUBA MERINA', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (298, 'LG02301', 'NGOURANE OUOLOF', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (299, 'LG02302', 'THIOLOM FALL', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (300, 'LG02303', 'SAGATTA GUETH', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (301, 'LG02304', 'KANENE NDIOB', 25);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (302, 'LG02305', 'LORO', 25);

--Commune - DPT LINGUERE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (303, 'LG03001', 'LINGUERE', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (304, 'LG03002', 'DAHRA', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (305, 'LG03003', 'MBEULEUKHE', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (306, 'LG03004', 'BARKEDJI', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (307, 'LG03005', 'GASSANE', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (308, 'LG03101', 'THIARNY', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (309, 'LG03102', 'THIEL', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (310, 'LG03103', 'BOULAL', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (311, 'LG03104', 'DEALY', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (312, 'LG03105', 'THIAMENE DJOLOF', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (313, 'LG03201', 'SAGATTA DJOLOF', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (314, 'LG03202', 'AFFE DJIOLOF', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (315, 'LG03203', 'DODJI', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (316, 'LG03204', 'LABGAR', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (317, 'LG03205', 'OUARKHOKH', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (318, 'LG03301', 'KAMB', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (319, 'LG03302', 'MBOULA', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (320, 'LG03303', 'TESSEKERE FORAGE', 26);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (321, 'LG03304', 'YANG-YANG', 26);

--Commune - DPT MATAM
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (322, 'MT01001', 'MATAM', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (323, 'MT01002', 'OUROSSOGUI', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (324, 'MT01003', 'THILOGNE', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (325, 'MT01004', 'NGUIDJILONE', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (326, 'MT01101', 'DABIA', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (327, 'MT01102', 'DES AGNAM', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (328, 'MT01103', 'OREFONDE', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (329, 'MT01104', 'BOKIDIAWE', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (330, 'MT01201', 'NABADJI CIVOL', 27);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (331, 'MT01202', 'OGO', 27);

--Commune - DPT KANEL
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (332, 'MT02001', 'KANEL', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (333, 'MT02002', 'OUAOUNDE', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (334, 'MT02003', 'SEMME', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (335, 'MT02004', 'DEMBANCANE', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (336, 'MT02101', 'HAMADY HOUNARE', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (337, 'MT02102', 'SINTHIOU BAMAMBE-BANADJI', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (338, 'MT02103', 'ODOBERE', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (339, 'MT02104', 'WOURO SIDY', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (340, 'MT02201', 'NDENDORY', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (341, 'MT02202', 'AOURE', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (342, 'MT02203', 'BOKILADJI', 28);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (343, 'MT02204', 'ORKADIERE', 28);

--Commune - DPT RANEROU FERLO
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (344, 'MT03001', 'RANEROU', 29);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (345, 'MT03101', 'LOUGRE THIOLY', 29);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (346, 'MT03102', 'OUDALAYE', 29);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (347, 'MT03103', 'VELINGARA', 29);

--Commune - DPT DAGANA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (348, 'SL03001', 'DAGANA', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (349, 'SL03002', 'RICHARD TOLL', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (350, 'SL03003', 'ROSSO', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (351, 'SL03004',  'GAE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (352, 'SL03005', 'ROSS-BETHIO', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (353, 'SL03006', 'NDOMBO SANDJIRY', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (354, 'SL03101', 'NGNITH', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (355, 'SL03102', 'DIAMA', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (356, 'SL03103', 'RONKH', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (357, 'SL03201', 'BOKHOL', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (358, 'SL03202', 'MBANE', 30);

-- Chaque Commune du Senegal est un point de vent  : La ZZ-SEDAB a d'autre point de vente en plus des commues 

INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1000, 'ZZ-SEDAB', 'GIE PELLITAL AERE LAO', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1001, 'ZZ-SEDAB', 'CNT IBEU SALL THIAGAR', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1002, 'ZZ-SEDAB', 'GIE NAXADY DERETE CHEIKH D', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1003, 'ZZ-SEDAB', 'UNION LOCALE RONKH', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1004, 'ZZ-SEDAB', 'UNION LOCALE DIAWAR', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1005, 'ZZ-SEDAB', 'UNION LOCALE WASSOUL', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1006, 'ZZ-SEDAB', 'UNION LOCALE MBOUNDOUM', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1007, 'ZZ-SEDAB', 'UNION LOCALE KHEUN', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1008, 'ZZ-SEDAB', 'UNION DES GIE GUEDE CHAN', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1009, 'ZZ-SEDAB', 'UNION DES GIES KASSACK NO', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1010, 'ZZ-SEDAB', 'UNION DES GIES DE MBAGAM', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1011, 'ZZ-SEDAB', 'UNION DEBY TIGUETTE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1012, 'ZZ-SEDAB', 'UNION DES GIES DE MBAGAM', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1013, 'ZZ-SEDAB', 'UNION DES GIES DE KASSACK', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1014, 'ZZ-SEDAB', 'ABDOU KHADRE NIANG', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1015, 'ZZ-SEDAB', 'DEPOT DE THIEWLE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1016, 'ZZ-SEDAB', 'MBAYE NDIAYE RICHARD TOLL', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1017, 'ZZ-SEDAB', 'GUIA DOYANE SILEYE BOCAR', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1018, 'ZZ-SEDAB', 'DEPOT DE NDIOUM', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1019, 'ZZ-SEDAB', 'DEPOT DE NDIOUM', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1020, 'ZZ-SEDAB', 'DEPOT SAED NHIANGA', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1021, 'ZZ-SEDAB', 'NDIOUGUE MERESSE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1022, 'ZZ-SEDAB', 'DEPOT DE TAREDJI', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1023, 'ZZ-SEDAB', 'GNITH', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1024, 'ZZ-SEDAB', 'USAGERS DE MAKA DIAMA', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1025, 'ZZ-SEDAB', 'ADAMA DIENG', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1026, 'ZZ-SEDAB', 'COMMISSION THIAGAR', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1027, 'ZZ-SEDAB', 'MEDINA DIATBE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1028, 'ZZ-SEDAB', 'MAGASIN PETE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1029, 'ZZ-SEDAB', 'DIOMANDOU', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1030, 'ZZ-SEDAB', 'MBAYE THIAM', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1031, 'ZZ-SEDAB', 'GAMOU THILLER', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1032, 'ZZ-SEDAB', 'GAYA', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1033, 'ZZ-SEDAB', 'GIE NATANGUE OUSSEYNOU', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1034, 'ZZ-SEDAB', 'GIE FALL ET FRERE ABDA FALL', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1035, 'ZZ-SEDAB', 'CHEIKH DIAW', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1036, 'ZZ-SEDAB', 'DAOUDA GAYE', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1037, 'ZZ-SEDAB', 'MADIOP', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1038, 'ZZ-SEDAB', 'BABA DIALLO', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1039, 'ZZ-SEDAB', 'AMADOU THIAM AERE LAO', 30);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1040, 'ZZ-SEDAB', 'NDIANDANE', 30);

------ END Point de vente Propre a ZZ-SEDAB


--Commune - DPT PODOR
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (359, 'SL02001', 'PODOR', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (360, 'SL02002', 'GOLLERE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (361, 'SL02003', 'NDIOUM', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (362, 'SL02004', 'NIANDANE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (363, 'SL02005', 'MBOUMA', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (364, 'SL02006', 'GUEDE CHANTIER', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (365, 'SL02007', 'DEMETTE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (366, 'SL02008', 'GALOYA TOUCOULEUR', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (367, 'SL02009', 'AERE LAO', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (368, 'SL02010', 'PETE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (369, 'SL02011', 'BODE LAO', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (370, 'SL02012', 'WALALDE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (371, 'SL02101', 'MERY', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (372, 'SL02102', 'DOUMGA LAO', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (373, 'SL02103', 'MADINA DIATHBE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (374, 'SL02201', 'GAMADJI SARE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (375, 'SL02202', 'DODEL', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (376, 'SL02203', 'GUEDE VILLAGE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (377, 'SL02301', 'FANAYE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (378, 'SL02302', 'NDIAYENE PEINDAO', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (379, 'SL02303', 'MBOLO BIRANE', 31);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (380, 'SL02304', 'BOKE DIALLOUBE', 31);

--Commune - DPT SAINT-LOUIS
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (381, 'SL01001', 'SAINT-LOUIS', 32);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (382, 'SL01002', 'MPAL', 32);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (383, 'SL01003', 'FASS NGOM', 32);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (384, 'SL01004', 'NDIEBENE GANDIOLE', 32);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (385, 'SL01005', 'GANDON', 32);

-- 

--Commune - DPT BOUNKILING
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (386, 'SD03001', 'BOUNKILING', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (387, 'SD03002', 'MADINA WANDIFA', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (388, 'SD03003', 'NDIAMACOUTA', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (389, 'SD03101', 'BOGAL', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (390, 'SD03102', 'TANKON', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (391, 'SD03103', 'NDIAMALATHIEL', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (392, 'SD03104', 'DJINANY', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (393, 'SD03201', 'DIACOUNDA', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (394, 'SD03202', 'INOR', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (395, 'SD03203', 'KANDION MANGANA', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (396, 'SD03204', 'BONA', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (397, 'SD03301', 'DIAMBATI', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (398, 'SD03302', 'FAOUNE', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (399, 'SD03303', 'DIAROUME', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (3999, 'SD033033', 'DATOR', 33);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (3998, 'SD033032', 'Darsalam Mbaye', 33);


--Commune - DPT GOUDOMP
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (400, 'SD02001', 'GOUDOMP', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (401, 'SD02002', 'SAMINE', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (402, 'SD02003', 'TANAFF', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (403, 'SD02004', 'DIATTACOUNDA', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (404, 'SD02101', 'YARANG BALANTE', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (405, 'SD02102', 'MANGAROUNGOU SANTO', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (406, 'SD02103', 'SIMBANDI BALANTE', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (407, 'SD02104', 'DJIBANAR', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (408, 'SD02105', 'KAOUR', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (409, 'SD02201', 'DIOUBOUDOU', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (410, 'SD02202', 'SIMBANDI BRASSOU', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (411, 'SD02203', 'BAGHERE', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (412, 'SD02204', 'NIAGHA', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (413, 'SD02301', 'KARANTABA', 34);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (414, 'SD02302', 'KOLIBANTANG', 34);

--Commune - DPT SEDHIOU
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (415, 'SD01001', 'SEDHIOU', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (416, 'SD01002', 'DIANNAH MALARY', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (417, 'SD01003', 'MARSASSOUM', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (418, 'SD01101', 'DJIREDJI', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (419, 'SD01102', 'BAMBALY', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (420, 'SD01201', 'OUDOUCAR', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (421, 'SD01202', 'SAMA KANTA PEULH', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (422, 'SD01203', 'DIANNAH BA', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (423, 'SD01204', 'KOUSSY SAKAR', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (424, 'SD01205', 'SAKAR', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (425, 'SD01206', 'DIENDE', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (426, 'SD01301', 'SAN SAMBA', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (427, 'SD01302', 'BEMET BIDJINI', 35);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (428, 'SD01303', 'DJIBABOUYA', 35);

--Commune - DPT BAKEL
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (429, 'TC02001', 'BAKEL', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (430, 'TC02002', 'DIAWARA', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (431, 'TC02003', 'KIDIRA', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (432, 'TC02101', 'BELE', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (433, 'TC02102', 'SINTHIOU FISSA', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (434, 'TC02201', 'TOUMBOURA', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (435, 'TC02202', 'SADATOU', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (436, 'TC02203', 'MADINA FOULBE', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (437, 'TC02204', 'GATHIARY', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (438, 'TC02301', 'MOUDERY', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (439, 'TC02302', 'BALLOU', 36);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (440, 'TC02303', 'GABOU', 36);

--Commune - DPT GOUDIRY
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (441, 'TC03001', 'GOUDIRY', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (442, 'TC03002', 'KOTHIARY', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (443, 'TC03101', 'BOYNGUEL BAMBA', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (444, 'TC03102', 'SINTHIOU MAMADOU BOUBOU', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (445, 'TC03103', 'KOUSSAN', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (446, 'TC03104', 'DOUGUE', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (447, 'TC03201', 'DIANKE MAKHA', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (448, 'TC03202', 'BOUTOUCOUFARA', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (449, 'TC03203', 'BANI ISRAEL', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (450, 'TC03204', 'KOMOTI', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (451, 'TC03301', 'SINTHIOU BOCAR ALY', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (452, 'TC03302', 'KOULOR', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (453, 'TC03401', 'BALA', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (454, 'TC03402', 'KOAR', 37);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (455, 'TC03403', 'GOUMBAYEL', 37);

--Commune - DPT KOUPENTOUM
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (456, 'TC04001', 'KOUPENTOUM', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (457, 'TC04002', 'MALEM NIANI', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (458, 'TC04101', 'NDAME', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (459, 'TC04102', 'MERETO', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (460, 'TC04103', 'KAHENE', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (461, 'TC04104', 'BAMBA THIALENE', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (462, 'TC04201', 'PAYAR', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (463, 'TC04202', 'KOUTHIA GUAYDI', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (464, 'TC04203', 'KOUTHIABA WOLOF', 38);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (465, 'TC04204', 'PASS KOTO', 38);

--Commune - DPT TAMBACOUNDA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (466, 'TC01001', 'TAMBACOUNDA', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (467, 'TC01101', 'NIANI TOUCOULEUR', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (468, 'TC01102', 'MAKACOLIBANTANG', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (469, 'TC01103', 'NDOGA BABACAR', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (470, 'TC01201', 'DIALACOTO', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (471, 'TC01202', 'MISSIRAH', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (472, 'TC01203', 'NETTEBOULOU', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (473, 'TC01301', 'SINTHIOU MALEME', 39);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (474, 'TC01302', 'KOUSSANAR', 39);


--Commune - DPT MBOUR
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (475, 'TH03001', 'MBOUR', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (476, 'TH03002', 'JOAL-FADIOUTH', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (477, 'TH03003', 'NGUEKHOKH', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (478, 'TH03004', 'THIADIAYE', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (479, 'TH03005', 'SALY PORTUDAL', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (480, 'TH03006', 'NGAPAROU', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (481, 'TH03007', 'SOMONE', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (482, 'TH03008', 'POPENGUINE', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (483, 'TH03101', 'FISSEL', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (484, 'TH03102', 'NDIAGANIAO', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (485, 'TH03201', 'SESSENE', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (486, 'TH03202', 'SANDIARA', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (487, 'TH03203', 'NGUENIENE', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (488, 'TH03301', 'SINDIA', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (489, 'TH03302', 'MALICOUNDA', 40);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (490, 'TH03303', 'DIASS', 40);

--Commune - DPT THIES
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (491, 'TH01001', 'THIES (VILLE)', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (492, 'TH01002', 'KAYAR', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (493, 'TH01003', 'KHOMBOLE', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (494, 'TH01004', 'POUT', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (495, 'TH01101', 'THIES NORD', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (496, 'TH01201', 'THIES EST', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (497, 'TH01202', 'THIES OUEST', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (498, 'TH01301', 'THIENABA', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (499, 'TH01302', 'NGOUDIANE', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (500, 'TH01303', 'NDIEYENE SIRAKH', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (501, 'TH01304', 'TOUBA TOUL', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (502, 'TH01401', 'KEUR MOUSSA', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (503, 'TH01402', 'DIENDER', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (504, 'TH01403', 'FANDENE', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (505, 'TH01501', 'NOTTO', 41);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (506, 'TH01502', 'TASSETE', 41);

--Commune - DPT TIVAOUANE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (507, 'TH02001', 'TIVAOUANE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (508, 'TH02002', 'MBORO', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (509, 'TH02003', 'MEKHE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (510, 'TH02101', 'MEOUANE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (511, 'TH02102', 'DAROU KHOUDOSS', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (512, 'TH02103', 'TAÏBA NDIAYE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (513, 'TH02201', 'MERINA DAKHAR', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (514, 'TH02202', 'KOUL', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (515, 'TH02203', 'PEKESSE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (516, 'TH02301', 'NIAKHENE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (517, 'TH02302', 'MBAYENE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (518, 'TH02303', 'THILMAKHA', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (519, 'TH02304', 'NGANDIOUF', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (520, 'TH02401', 'NOTTO GOUYE DIAMA', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (521, 'TH02402', 'MONT ROLLAND', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (522, 'TH02403', 'PIRE GOUREYE', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (523, 'TH02404', 'CHERIF LO', 42);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (524, 'TH02405', 'PAMBAL', 42);

--Commune - DPT BIGNONA
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (525, 'ZG03001', 'BIGNONA', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (526, 'ZG03002', 'THIONCK-ESSYL', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (527, 'ZG03003', 'DIOULOULOU', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (528, 'ZG03101', 'KATABA 1', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (529, 'ZG03102', 'DJINAKY', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (530, 'ZG03103', 'KAFOUNTINE', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (531, 'ZG03201', 'TENGHORI', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (532, 'ZG03202', 'NIAMONE', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (533, 'ZG03203', 'OUONCK', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (534, 'ZG03204', 'COUBALAN', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (535, 'ZG03301', 'BALINGHORE', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (536, 'ZG03302', 'DIEGOUNE', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (537, 'ZG03303', 'KARTIACK', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (538, 'ZG03304', 'MANGAGOULACK', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (539, 'ZG03305', 'MLOMP', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (540, 'ZG03401', 'DJIBIDIONE', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (541, 'ZG03402', 'OULAMPANE', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (542, 'ZG03403', 'SINDIAN', 43);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (543, 'ZG03404', 'SUELLE', 43);

--Commune - DPT OUSSOUYE
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (544, 'ZG02001', 'OUSSOUYE', 44);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (545, 'ZG02101', 'DIEMBERING', 44);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (546, 'ZG02102', 'SANTHIABA MANJACK', 44);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (547, 'ZG02201', 'OUKOUT', 44);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (548, 'ZG02202', 'MLOMP', 44);

--Commune - DPT ZIGUINCHOR
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (549, 'ZG01001', 'ZIGUINCHOR', 45);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (550, 'ZG01101', 'NIAGUIS', 45);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (551, 'ZG01102', 'ADEANE', 45);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (552, 'ZG01103', 'BOUTOUPA CAMARACOUNDA', 45);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (553, 'ZG01201', 'NIASSIA', 45);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (554, 'ZG01202', 'ENAMPORE', 45);
------------------ Communes ------------------------




