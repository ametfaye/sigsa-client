

------- INTRANT  (ARBRE INTRANT) / TYPE --> CATEGORIE -> VARIETE -> INTRANT
--- TYPE INTRANT----------------
INSERT INTO public.type_intrant( type_intrant_id, code, libelle, picto) VALUES (1, '1EN', 'Engrais', '1en');
INSERT INTO public.type_intrant( type_intrant_id, code, libelle, picto) VALUES (2, '2SEM', 'Semence', '2sem');
INSERT INTO public.type_intrant( type_intrant_id, code, libelle, picto) VALUES (3, '3HER', 'Materiels', '3her');
INSERT INTO public.type_intrant( type_intrant_id, code, libelle, picto) VALUES (4, '4MAT', 'Herbicides', '4mat');
INSERT INTO public.type_intrant( type_intrant_id, code, libelle, picto) VALUES (5, '5MAT', 'Produit Phytosanitaire', '5ph');
INSERT INTO public.type_intrant( type_intrant_id, code, libelle, picto) VALUES (6, '6PA', 'Produit Antiparasitaire', '6pa');


---- Catégorie---------------------------------------------------
-----------------------------------------------------------------
---CATEGORIE ET VARIETE DES UREE
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle , picto) VALUES (1, 1, '1UR', 'UREE', 'uree.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (1, '1UR', 'UREE', 'uree', 1);
---		
			
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle , picto) VALUES (2, 1, '2DP', 'DAP' , 'dap.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (2, '2DAP', 'DAP', 'dap', 2);
---			
			
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (3, 1, '3DSP', 'DSP' , 'dsp.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (3, '2DSP', 'DSP', 'dsp', 3);
---
			
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle , picto) VALUES (4, 1, '4E', 'NPK' , 'npk.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (4, '062010', 'NPK 06-20-10', 'npk062010', 4);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (5, '092330', 'NPK 09-23-30', 'npk092330', 4);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (6, '101010', 'NPK 10-10-20', 'npk101020', 4);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (7, '151010', 'NPK 15-10-10', 'npk151010', 4);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (8, '151515', 'NPK 15-15-15', 'npk151515', 4);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (9, '062010', 'NPK BULK 06-20-10', 'npkbulk062010', 4);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (10, '151010','NPK BULK 15-10-10', 'npk151010', 4);
---
			
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (5, 1, '6TGE', 'TOSS-GUI' , 'tossgui.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (11, '11TG','TOSS-GUI', 'tossgui', 5);
						
-------------------------------------------------------------


---CATEGORIE ET VARIETEE DE SEMENCES D ARACHIDE 
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (6, 2, '2SEM', 'Semences d arachide' , 'semencedarachide.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (1111, '28206', '28-206', '28206', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (12, '5533', '55-33', '5533', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (13, '55347', '55-347', '55347', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (14, '57313', '57-313', '57313', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (15, '57422', '57-422', '57422', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (16, '69101', '69-101', '69101', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (17, '7300', '73-00', '7-00', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (18, '7328', '73-28', '7328', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (19, '7377', '73-27', '7377', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (20, '73911', '73 9-11', '73911', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (21, '73-33', '73-33', '7333', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (22, '776A', '776-A', '776A', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (23, '78937', '78 937', '78937', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (24, 'r2', 'Arachides R2', 'r2', 6); 
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (25, 'r3', 'Arachides R3', 'r3', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (26, 'aecreme', 'Arachide écrémé', 'aecreme', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (27, 'fleure11', 'FLEURE 11', 'FLEURE11', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (28, 'GC8-35', 'GC 8-35', 'GC8-35', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (29, 'SRV119', 'SRV 1-19', 'SRV119', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (30, 'GH11920', 'GH 119-20', 'GH11920', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (31, 'PC79-79', 'PC 79-79', 'PC7979', 6);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (32, 'H750', 'H 75-0', 'H750', 6);
			
-------

---CATEGORIE ET VARIETEE  DE SEMENCES  DE MAIS 
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (7, 2, '6MA', 'Semences de mais' , 'semencemais.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (33, '110T', 'OBATAMPA', 'OBATAMPA', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (34, 'SWAN', 'SWAN', 'swan', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (35, 'XG', 'Xeweul Gi', 'Xeweulgi', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (36, 'nr', 'NOOR 96', 'noor96', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (37, 'dm', 'DOO MER', 'doomer', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (38, 'Sooror', 'Sooror', 'sooror', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (39, 'gaawnaa', 'Gaawnaa', 'gaawnaa', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (40, 'jaboot', 'Jaboot', 'jaboot', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (41, 'gy', 'Goor Yomboul', 'gaawnGooryomboulaa', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (42, 'ys', 'Yaayi Seex', 'yaayiseex', 7);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (43, 'r1', 'R1', 'r1', 7);
			
----

---CATEGORIE ET VARIETEE DE SEMENCES  DE NIEBE 
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (8, 2, '8SMN', 'Semences de niébé' , 'semenceniebe.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (44, '110T', 'MELAKH', 'melakh', 8);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (45, 'SWAN', 'MOUGNE', 'mougn', 8);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (46, 'XG', 'AERLY TA√è', 'aerly', 8);
--------

---CATEGORIE ET VARIETEE  DE SEMENCES DE POMME DE TERRES 
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (9, 2, '8MA', 'Semences de pomme de terre' , 'semencepommedeterre.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (47, '', 'Anais', 'Anais', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (48, '', 'Anais', 'Anais 28-35', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (49, '', 'DOUNIA', 'dounia', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (50, '', 'DOUNIA 28/55', 'dounia2855', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (51, 'STP', 'SPUNTA', 'spuntu', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (52, '', 'SPUNTA 28/55', 'spunta2855', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (53, '', 'SPUNTA 35/55 ', 'spunta3555', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (54, '', 'SPUNTA 35/35 ', 'spunta3535', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (55, '', 'KENZA', 'kenza', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (56, '', 'KENZA 28/55', 'kenza2855', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (57, '', 'EL BEIDA 35/60', 'elbeida6065', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (58, '', 'EL BEIDA 60/65', 'elbeida6065', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (59, '', 'SPEEDA 28/55', 'speeda2855', 9);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (60, '', 'UNIVERSA 28/55', 'universa2855', 9);
---------

---CATEGORIE ET VARIETEE  DE SEMENCES DE POMME DE TERRES 
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (10, 2, '10MRMA', 'Semences de riz' , 'semenceriz.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (61, '', 'NERICA 1', 'nerica1', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (62, '', 'NERICA 2', 'nerica3', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (63, '', 'NERICA 3', 'nerica3', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (64, '', 'NERICA 4', 'nerica4', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (65, '', 'NERICA 5', 'nerica5', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (66, '', 'NERICA 6', 'nerica6', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (67, '', 'NERICA S-19', 'nericas19', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (68, '', 'NERICA S-21', 'nericas21', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (69, '', 'NERICA S-36', 'nericas36', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (70, '', 'NERICA S-44', 'nericas44', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (71, '', 'SAHEL 108', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (72, '', 'SAHEL 134', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (73, '', 'SAHEL 159', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (74, '', 'SAHEL 177', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (75, '', 'SAHEL 208', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (76, '', 'SAHEL 209', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (77, '', 'SAHEL 201', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (78, '', 'SAHEL 202', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (79, '', 'SAHEL 217', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (80, '', 'SAHEL 222', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (81, '', 'SAHEL 305', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (82, '', 'SAHEL 328', 'sahel', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (83, '', 'FKR 19', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (84, '', 'WAR 77-3-2-2', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (85, '', 'BG 90-2', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (86, '', 'TS2', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (87, '', 'FKR 45 N', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (88, '', 'ITA 150', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (89, '', 'FKR 19', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (90, '', 'WAR 56 50', '', 10);			
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (91, '', 'FKR 19', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (92, '', 'DJ 684-D', '', 10);			
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (93, '', 'DJ 11-509', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (94, '', 'DJ 12-519', '', 10);			
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (95, '', 'BW 248-1', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (96, '', 'WAR 77-3-2-2', '', 10);			
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (97, '', 'BR 51-46-5', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (98, '', 'ITA 123', '', 10);			
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (99, '', 'TOX 728-1', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (100, '', 'ROK 5', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (101, '', 'WAR 1', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (102, '', 'WAR 81-2-1-3-2', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (103, '', 'IKONG PAO', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (104, '', 'IR 8', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (105, '', 'Jaya', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (106, '', 'IR 1529-680-3', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (107, '', 'IR 442', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (108, '', 'KH 998', '', 10);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (109, '', 'KWAN SHE SHUMG', '', 10);

-----

---CATEGORIE ET VARIETEE DE SEMENCES  DE MIL 
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (11, 2, '8SMN', 'Semences de mil' , 'semencemil.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (110, 'souna', 'SOUNA 3', 'souna', 11);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (111, 'SWAN', 'IBV 8001', 'ibv', 11);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (112, 'XG', 'IBV 8004', 'ibv', 11);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (113, '110T', 'IBV 8402', 'ibv', 11);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (114, 'is', 'ISMI94 07', 'mougn', 11);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (115, 'gaw', 'GAWANE', 'gawane', 11);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (116, 'thi', 'THIALACK 2', 'thi', 11);	
			
----

---CATEGORIE ET VARIETEE  HERBICIDE
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle, picto) VALUES (12, 4, '13HER', 'Herbicide' , '5ph.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (117, '', 'PROPANIL', 'propanal', 12);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (118, '', 'WEEDON', 'weedon', 12);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (119, '', 'OXADIAZON', 'oxadiazon', 12);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (120, '-', 'RUND UP', 'rundup', 12);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (121, '', 'GLYPHOSATE', 'glyphosate', 12);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (122, '', 'IKOKADIGNE', 'ikokadigne', 12);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (123, '', 'ALLIGATOR', 'alligator', 12);	
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (124, '', 'EUREKA', 'eureka', 12);	



---CATEGORIE ET VARIETEE  MATERIElS
INSERT INTO public.categorie_intrant(categorie_intrant_id, type_intrant_id, code, libelle , picto) VALUES (13, 3, '0MATSMN', 'Materiel agricole' , '4mat.png');
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (125, '', 'Motoculteur', 'motoculteur', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (126, '', 'Tarière', 'tariere', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (127, '', 'Pompe', 'pompe', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (128, '', 'Varan', 'varan', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (129, '', 'Broyeur', 'broyeur', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (130, '', 'Tracteur', 'tracteur', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (131, '', 'Charrue', 'chrrue', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (133, '', 'houes', 'houe', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (134, '', 'Sémoirs', 'semoir', 13);
			INSERT INTO public.variete_intrant(variete_intrant_id, code, libelle, picto, categorie_intrant_id) VALUES (135, '', 'Bateuse', 'bateuse', 13);

--- INTRANT

	
