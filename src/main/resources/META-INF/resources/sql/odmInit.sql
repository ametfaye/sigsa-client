INSERT INTO public.services( srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (2, 'TEST' , 'Ministère des affaires étrangères', 1, 1);
INSERT INTO public.services( srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (3, 'EA' , 'Assemblée nationale', 1, 1);
INSERT INTO public.services( srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (4, 'MF' , 'Ministère des finances', 1, 1);
INSERT INTO public.services( srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (5, 'MA' , 'Ministère de agriculture', 1, 1);

--- ETAT ORDRE DE MISSION
INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (1, 'EA', 'ENREGISTRE');
INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (2, 'TS', 'Transmis au SG');
INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (3, 'TSGG', 'Transmis au SGG');

INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (4, 'ASG', 'Accepté par le SG');
INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (5, 'RSG', 'Refusé par le SG');

INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (6, 'ASGG', 'Accepté par le SGG');
INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (7, 'RSGG', 'Refusé par le SGG');

INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (8, 'BE', 'Billets édités');
INSERT INTO public.etat_ordre_de_mission(eodm_id, code_etat, libelle_etat)VALUES (9, 'RSGG', 'ECHU');



--- Compagnie
INSERT INTO public.compagnie( com_id, code, libelle) VALUES (1, 'AS', 'Air Sénégal');
INSERT INTO public.compagnie( com_id, code, libelle) VALUES (2, 'AF', 'Air France');
INSERT INTO public.compagnie( com_id, code, libelle) VALUES (3, 'AM', 'Royal Air Maroc');
INSERT INTO public.compagnie( com_id, code, libelle) VALUES (4, 'DL', 'Delta');


--- Destinations
INSERT INTO public.destination( dst_id, dest_code, destination_libelle, com_id) VALUES (0, 'BL', 'BAMAKO', 1);
INSERT INTO public.destination( dst_id, dest_code, destination_libelle, com_id) VALUES (1, 'PRS', 'PARIS', 1);
INSERT INTO public.destination( dst_id, dest_code, destination_libelle, com_id) VALUES (2, 'ML', 'MILAN', 1);
INSERT INTO public.destination( dst_id, dest_code, destination_libelle, com_id) VALUES (3, 'BL', 'BRUXELLES', 1);
INSERT INTO public.destination( dst_id, dest_code, destination_libelle, com_id) VALUES (4, 'BL', 'NEW YORK', 1);
INSERT INTO public.destination( dst_id, dest_code, destination_libelle, com_id) VALUES (5, 'BL', 'BERLIN', 1);



--- Destination deservies
--AS = Paris et Bruxelles
INSERT INTO public.dest_deservies(com_id, dest_id) VALUES (1, 1);
INSERT INTO public.dest_deservies(com_id, dest_id) VALUES (1, 3);
--AF = Paris
INSERT INTO public.dest_deservies(com_id, dest_id) VALUES (2, 1);




---- Ministere

INSERT INTO public.ref_entite(ref_id, code, libelle) VALUES ( nextval('seq_entitePublic'), 'MFA', 'Ministère des Forces armées' );
INSERT INTO public.ref_entite(ref_id, code, libelle) VALUES ( nextval('seq_entitePublic'), 'MFB', 'Ministère des Finances et du Budget ' );
INSERT INTO public.ref_entite(ref_id, code, libelle) VALUES ( nextval('seq_entitePublic'), 'MGSMJ', 'Ministère de la justice , Garde des Sceaux' );
INSERT INTO public.ref_entite(ref_id, code, libelle) VALUES ( nextval('seq_entitePublic'), 'MAESE', 'Ministre des Affaires étrangères, et des Sénégalais de l Extérieur' );
INSERT INTO public.ref_entite(ref_id, code, libelle) VALUES ( nextval('seq_entitePublic'), 'MIN', 'Ministère de l Intérieur ' );

















--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.26
-- Dumped by pg_dump version 12.2

-- Started on 2022-09-10 18:08:58 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2492 (class 0 OID 55637)
-- Dependencies: 181
-- Data for Name: pays; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2494 (class 0 OID 55647)
-- Dependencies: 183
-- Data for Name: regn_region; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2489 (class 0 OID 55616)
-- Dependencies: 178
-- Data for Name: departement; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2486 (class 0 OID 55598)
-- Dependencies: 175
-- Data for Name: commune; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2484 (class 0 OID 55588)
-- Dependencies: 173
-- Data for Name: adrs_adresse; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.adrs_adresse (adrs_id, codepostal, libelle, quartier, ville, commune_id, departement_id, regn_id) VALUES (1, '99999', 'Boulevard Djilly Mbaye', 'Perception de Dakar Centre', 'DAKAR', NULL, NULL, NULL);


--
-- TOC entry 2503 (class 0 OID 60205)
-- Dependencies: 192
-- Data for Name: agence; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2505 (class 0 OID 60218)
-- Dependencies: 194
-- Data for Name: compagnie; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.compagnie (com_id, code, libelle) VALUES (1, 'AS', 'Air Sénégal');
INSERT INTO public.compagnie (com_id, code, libelle) VALUES (2, 'AF', 'Air France');
INSERT INTO public.compagnie (com_id, code, libelle) VALUES (3, 'AM', 'Royal Air Maroc');
INSERT INTO public.compagnie (com_id, code, libelle) VALUES (4, 'DL', 'Delta');


--
-- TOC entry 2504 (class 0 OID 60213)
-- Dependencies: 193
-- Data for Name: agence_compagnies; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2487 (class 0 OID 55606)
-- Dependencies: 176
-- Data for Name: cont_contact; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cont_contact (cont_id, courriel, fax, fixe, libelle, mobile, site_web) VALUES (1, 'contact@servicepublic.sn', '33 822 50 30', '33 822 50 30', 'Perception de Dakar Centre', '33 822 50 30', 'www.servicepublic.sn');


--
-- TOC entry 2510 (class 0 OID 84777)
-- Dependencies: 199
-- Data for Name: grade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.grade (g_id, code, libelle) VALUES (0, '0PCECE', 'Principal de classe exceptionnelle Classe exceptionnelle');
INSERT INTO public.grade (g_id, code, libelle) VALUES (1, '0PCECE', 'Première Classe (1re)  - 2e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (2, '0PCECE', 'Première Classe (1re)  - 1e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (3, '0PCECE', 'Deuxième (2e) Classe - 2e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (4, '0PCECE', 'Deuxième (2e) Classe - 1e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (5, '0PCECE', 'Troixième (3e) Classe - 2e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (6, '0PCECE', 'Troixième (3e) Classe - 1e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (7, '0PCECE', 'Quatrième (4e) Classe - 2e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (8, '0PCECE', 'Quatrième (4e) Classe - 1e échelon');
INSERT INTO public.grade (g_id, code, libelle) VALUES (9, '9STA', 'Stagiaire');


--
-- TOC entry 2509 (class 0 OID 84772)
-- Dependencies: 198
-- Data for Name: fonction; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (0, '0PPCS', 'Premier Président de la Cour suprême', 1);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (1, '1PS', 'Procureur Général près la Cour suprême', 2);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (2, '2PS', 'Président de Chambre à la Cour suprême', 3);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (3, '3PA', 'Procureur général près une Cour d’appel', 4);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (4, '4M', 'Ministre', 5);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (5, '5D', 'Directeur', 5);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (6, '5SM', 'Sécrétaire Ministre', 6);
INSERT INTO public.fonction (id, code, libelle, g_id) VALUES (7, '7SGD', 'Sécrétaire Gouvernement', 7);


--
-- TOC entry 2512 (class 0 OID 84787)
-- Dependencies: 201
-- Data for Name: personne_physique; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personne_physique (pph_id, nom, prenom, sexe) VALUES (30, 'faye', 'mouhamed', 'mouhamed');
INSERT INTO public.personne_physique (pph_id, nom, prenom, sexe) VALUES (35, 'Gael', 'Awa', 'Soxk');
INSERT INTO public.personne_physique (pph_id, nom, prenom, sexe) VALUES (38, 'Najim', 'Samer', 'Sboui');
INSERT INTO public.personne_physique (pph_id, nom, prenom, sexe) VALUES (46, 'DIOP', 'ABDOU', 'ABDOU');
INSERT INTO public.personne_physique (pph_id, nom, prenom, sexe) VALUES (49, 'Sofiene', 'AJili', 'AJ');


--
-- TOC entry 2502 (class 0 OID 60139)
-- Dependencies: 191
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.services (srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (1, 'TEST', 'Ministère des sport ', 1, 1);
INSERT INTO public.services (srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (2, 'TEST', 'Minitère de l agriculture', 1, 1);
INSERT INTO public.services (srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (3, 'EA', 'Assemblée nationale', 1, 1);
INSERT INTO public.services (srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (4, 'TEST', 'Ministère des finances', 1, 1);
INSERT INTO public.services (srv_id, code_service, libelle_service, adrs_id, cont_id) VALUES (5, 'TEST', 'Ministère de agriculture', 1, 1);


--
-- TOC entry 2508 (class 0 OID 84747)
-- Dependencies: 197
-- Data for Name: agent; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.agent (a_id, indice, matricule, f_id, pph_id, srv_id) VALUES (31, NULL, 'MF123', 0, 30, NULL);
INSERT INTO public.agent (a_id, indice, matricule, f_id, pph_id, srv_id) VALUES (36, NULL, 'GP123', 2, 35, NULL);
INSERT INTO public.agent (a_id, indice, matricule, f_id, pph_id, srv_id) VALUES (39, NULL, 'ND986', 2, 38, NULL);
INSERT INTO public.agent (a_id, indice, matricule, f_id, pph_id, srv_id) VALUES (47, NULL, 'DP123456', 1, 46, NULL);
INSERT INTO public.agent (a_id, indice, matricule, f_id, pph_id, srv_id) VALUES (50, NULL, 'SAJ1234', 2, 49, NULL);


--
-- TOC entry 2485 (class 0 OID 55593)
-- Dependencies: 174
-- Data for Name: calepin_caisse_comptable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.calepin_caisse_comptable (calpin_id, montant_solde_final, montant_solde_intial, montant_total_deltat_recette_etversement, montant_total_depense, montant_total_recette, nb_billet1000, nb_billet10000, nb_billet2000, nb_billet500, nb_billet5000, nb_pieces1, nb_pieces10, nb_pieces100, nb_pieces200, nb_pieces25, nb_pieces250, nb_pieces5, nb_pieces50, nb_pieces500) VALUES (3, NULL, 0, 1000, 0, 1000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);


--
-- TOC entry 2488 (class 0 OID 55611)
-- Dependencies: 177
-- Data for Name: decomposition_journee_comptable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.decomposition_journee_comptable (id_decomposition, nb_billet1000, nb_billet10000, nb_billet2000, nb_billet500, nb_billet5000, nb_pieces1, nb_pieces10, nb_pieces100, nb_pieces200, nb_pieces25, nb_pieces250, nb_pieces5, nb_pieces50, nb_pieces500) VALUES (1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);


--
-- TOC entry 2506 (class 0 OID 60256)
-- Dependencies: 195
-- Data for Name: destination; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.destination (dst_id, dest_code, destination_libelle, com_id, regn_id) VALUES (0, 'BL', 'BAMAKO', 1, NULL);
INSERT INTO public.destination (dst_id, dest_code, destination_libelle, com_id, regn_id) VALUES (1, 'PRS', 'PARIS', 1, NULL);
INSERT INTO public.destination (dst_id, dest_code, destination_libelle, com_id, regn_id) VALUES (2, 'ML', 'MILAN', 1, NULL);
INSERT INTO public.destination (dst_id, dest_code, destination_libelle, com_id, regn_id) VALUES (3, 'BL', 'BRUXELLES', 1, NULL);
INSERT INTO public.destination (dst_id, dest_code, destination_libelle, com_id, regn_id) VALUES (4, 'BL', 'NEW YORK', 1, NULL);
INSERT INTO public.destination (dst_id, dest_code, destination_libelle, com_id, regn_id) VALUES (5, 'BL', 'BERLIN', 1, NULL);


--
-- TOC entry 2507 (class 0 OID 60284)
-- Dependencies: 196
-- Data for Name: dest_deservies; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.dest_deservies (com_id, dest_id) VALUES (1, 1);
INSERT INTO public.dest_deservies (com_id, dest_id) VALUES (1, 3);
INSERT INTO public.dest_deservies (com_id, dest_id) VALUES (2, 1);


--
-- TOC entry 2499 (class 0 OID 60113)
-- Dependencies: 188
-- Data for Name: etat_ordre_de_mission; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (1, 'EA', 'ENREGISTRE');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (2, 'TS', 'Tranmis au SG');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (3, 'TSGG', 'Transmis au SGG');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (4, 'ASG', 'Accepté par le SG');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (5, 'RSG', 'Refusé par le SG');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (6, 'ASGG', 'Accepté par le SGG');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (7, 'RSGG', 'Refusé par le SGG');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (8, 'BE', 'Billets édités');
INSERT INTO public.etat_ordre_de_mission (eodm_id, code_etat, libelle_etat) VALUES (9, 'RSGG', 'ECHU');


--
-- TOC entry 2511 (class 0 OID 84782)
-- Dependencies: 200
-- Data for Name: groupe; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.groupe (gr_id, code, libelle) VALUES (100, 'GA', 'A');
INSERT INTO public.groupe (gr_id, code, libelle) VALUES (0, 'GA', 'A');
INSERT INTO public.groupe (gr_id, code, libelle) VALUES (1, 'GA', 'B1');
INSERT INTO public.groupe (gr_id, code, libelle) VALUES (2, '1G', 'A3');
INSERT INTO public.groupe (gr_id, code, libelle) VALUES (3, '2F', 'F');
INSERT INTO public.groupe (gr_id, code, libelle) VALUES (4, 'CF', 'C');


--
-- TOC entry 2493 (class 0 OID 55642)
-- Dependencies: 182
-- Data for Name: poste_comptable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (2, 'Paierie générale du Trésor', 500000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (3, 'Recette générale du Trésor', 100000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (4, 'Trésorerie Paierie pour l’Etranger', 15000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (5, 'Agence comptable des grands Projets', 15000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (45, 'Perception de Dakar-Port', 120000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (46, 'Perception de l aéroport international Blaise DIAGNE', 120000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (47, 'Recette Perception municipale de Dakar', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (48, 'Perception de Pikine', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (49, 'Recette Perception de Dakar Plateau', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (50, 'Recette Perception de Dakar Bourguiba', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (6, 'Trésorerie Paierie régionale de Kaolack', 12000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (7, 'Recette Perception municipale de Thiès', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (10, 'Trésorerie paierie régionale de Diourbel', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (11, 'Recette perception municipale de Diourbel', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (12, 'Perception de Mbacké', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (13, 'Perception de Bambey', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (14, 'Trésorerie paierie régionale de Fatick', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (15, 'Perception de Foundiougne', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (16, 'Perception de Gossas', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (17, 'Trésorerie paierie régionale de Kaolack', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (18, 'Recette perception municipale de  Kaolack', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (19, 'Perception de Kaffrine', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (20, 'Perception de Nioro du Rip', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (21, 'Trésorerie paierie régionale de Kolda', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (22, 'Perception de Vélingara', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (23, 'Perception de Sédhiou', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (24, 'Trésorerie paierie régionale de Louga', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (25, 'Recette perception municipal de Louga', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (26, 'Perception de Kébémer', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (27, 'Perception de Linguère', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (28, 'Recette Perception municipale de Saint-Louis', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (29, 'Perception de Dagana', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (30, 'Perception de Podor', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (31, 'Perception de Linguère', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (32, 'Perception de Matam', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (33, 'Trésorerie Paierie régionale de Thiès', 12000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (34, 'Recette perception municipal de Thiès', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (35, 'Perception de Mbour', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (36, 'Perception de Tivaouane', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (37, 'Recette Perception municipale de Ziguinchor', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (38, 'Perception de Bignona', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (39, 'Perception de Oussouye', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (40, 'Perception de Tivaouane', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (41, 'Trésorerie paierie régionale de Tambacounda', 10000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (42, 'Perception de Bakel', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (43, 'Perception de Kédougou', 8000000, NULL, 1, NULL, 1);
INSERT INTO public.poste_comptable (poste_compable_id, libelle, seuil_alerte_versement, solde_initial_du_poste, adrs_id, calpin_id, cont_id) VALUES (1, 'CAGES', 800000000, 1000, 1, 3, 1);


--
-- TOC entry 2497 (class 0 OID 55662)
-- Dependencies: 186
-- Data for Name: user_infos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_infos (user_id, active, email, identifiant, last_name, name, password, poste_compable_id, srv_id) VALUES (0, 1, 'amet', '775311190', 'consultation', 'consultation', '$2a$10$7.wfnvMzN9ZpSeXJAUMQT.k1onfXzLqAAR.KqrsqLTx5Lm6Y7az/e', 1, 1);
INSERT INTO public.user_infos (user_id, active, email, identifiant, last_name, name, password, poste_compable_id, srv_id) VALUES (4, 1, 'chefposte', '775311190', 'consultation', 'consultation', '$2a$10$7.wfnvMzN9ZpSeXJAUMQT.k1onfXzLqAAR.KqrsqLTx5Lm6Y7az/e', 1, 1);
INSERT INTO public.user_infos (user_id, active, email, identifiant, last_name, name, password, poste_compable_id, srv_id) VALUES (1, 1, 'admin', '646568330', 'admin', 'admin', '$2a$10$7.wfnvMzN9ZpSeXJAUMQT.k1onfXzLqAAR.KqrsqLTx5Lm6Y7az/e', 1, 1);
INSERT INTO public.user_infos (user_id, active, email, identifiant, last_name, name, password, poste_compable_id, srv_id) VALUES (2, 1, 'poste', '646568330', 'admin', 'admin', '$2a$10$7.wfnvMzN9ZpSeXJAUMQT.k1onfXzLqAAR.KqrsqLTx5Lm6Y7az/e', 5, 1);
INSERT INTO public.user_infos (user_id, active, email, identifiant, last_name, name, password, poste_compable_id, srv_id) VALUES (52, 1, 'azert', 'azert', 'Ameth', 'Faye', '$2a$10$7yUlXVxtXRNKBhgNg21Beub7oop5YwnRQ/AR9S7C6uovJk7OaVVHK', 45, 1);


--
-- TOC entry 2490 (class 0 OID 55621)
-- Dependencies: 179
-- Data for Name: journee_comptable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.journee_comptable (idjc, date_comptable, date_validation, datemodification, datesaisie, id_auteur_validation, montant_total_depense, montant_total_recette, motif_refus, nom_auteur_validation, solde_journee_comptable, solde_journee_initial_aprest_validation_encaisse, solde_journee_initial_avant_validation_encaisse, statut, user_id, id_decomposition, poste_id) VALUES (2, '08-10-2020', '24-10-2020 07:28', NULL, '24-10-2020 05:30', NULL, 0, 1000, NULL, 'consultation consultation', 1000, -1000, 0, 1, 0, 1, 1);


--
-- TOC entry 2501 (class 0 OID 60131)
-- Dependencies: 190
-- Data for Name: evenement_ordre_de_mission; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.evenement_ordre_de_mission (event_id, code_evenement, date_evenement, libelle_evenement, adrs_id, user_id) VALUES (1, 'EAB', '2020-11-02', 'CREATION', 1, 0);
INSERT INTO public.evenement_ordre_de_mission (event_id, code_evenement, date_evenement, libelle_evenement, adrs_id, user_id) VALUES (2, 'EAC', '2020-11-02', 'ACCEPTATION SG', 1, 2);
INSERT INTO public.evenement_ordre_de_mission (event_id, code_evenement, date_evenement, libelle_evenement, adrs_id, user_id) VALUES (3, 'ESG', '2020-11-02', 'ACCEPTATION SGG', 1, 4);


--
-- TOC entry 2500 (class 0 OID 60121)
-- Dependencies: 189
-- Data for Name: odm_events; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2513 (class 0 OID 84807)
-- Dependencies: 202
-- Data for Name: ordre_de_mission; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ordre_de_mission (odm_id, budget, date_creation, date_debut_mission, date_fin_mission, date_modification, imputation, intitule, motif, objet, a_id, dst_id, eodm_id, srv_id) VALUES (44, NULL, '2022-08-29', '2022-08-05', '2022-08-05', NULL, NULL, NULL, '', NULL, 31, 1, 1, NULL);
INSERT INTO public.ordre_de_mission (odm_id, budget, date_creation, date_debut_mission, date_fin_mission, date_modification, imputation, intitule, motif, objet, a_id, dst_id, eodm_id, srv_id) VALUES (45, NULL, '2022-08-29', '2022-08-02', '2022-08-02', NULL, NULL, NULL, 'MISSION DIPLOMATIque pour cages', NULL, 31, 2, 1, NULL);
INSERT INTO public.ordre_de_mission (odm_id, budget, date_creation, date_debut_mission, date_fin_mission, date_modification, imputation, intitule, motif, objet, a_id, dst_id, eodm_id, srv_id) VALUES (48, NULL, '2022-08-29', '2022-09-05', '2022-09-05', NULL, NULL, NULL, 'Mission de test pour Paris', NULL, 47, 1, 1, NULL);
INSERT INTO public.ordre_de_mission (odm_id, budget, date_creation, date_debut_mission, date_fin_mission, date_modification, imputation, intitule, motif, objet, a_id, dst_id, eodm_id, srv_id) VALUES (51, NULL, '2022-08-30', '2022-08-01', '2022-08-01', NULL, NULL, NULL, 'ceci est une mission de test pour aller à Paris', NULL, 50, 3, 1, NULL);
INSERT INTO public.ordre_de_mission (odm_id, budget, date_creation, date_debut_mission, date_fin_mission, date_modification, imputation, intitule, motif, objet, a_id, dst_id, eodm_id, srv_id) VALUES (53, NULL, '2022-09-03', '2022-09-22', '2022-09-22', NULL, NULL, NULL, 'dddd', NULL, 31, 1, 1, NULL);
INSERT INTO public.ordre_de_mission (odm_id, budget, date_creation, date_debut_mission, date_fin_mission, date_modification, imputation, intitule, motif, objet, a_id, dst_id, eodm_id, srv_id) VALUES (54, NULL, '2022-09-03', '2022-09-05', '2022-09-05', NULL, NULL, NULL, 'samedi', NULL, 36, 1, 1, NULL);


--
-- TOC entry 2491 (class 0 OID 55629)
-- Dependencies: 180
-- Data for Name: parametrages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.parametrages (params_id, contexte, cron_expression, directoryftp, indice_majore, portftp, serveurftp, userftp, userftppass) VALUES (0, 'TEST', '* *  * * * 1', '/home/ftp/depotCGOS', 4.68599999999999994, '22', '10.125.12.12', 'root', '');


--
-- TOC entry 2514 (class 0 OID 92894)
-- Dependencies: 203
-- Data for Name: ref_entite; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ref_entite (ref_id, code, libelle) VALUES (2, 'MFA', 'Ministére des Forces armées');
INSERT INTO public.ref_entite (ref_id, code, libelle) VALUES (3, 'MFA', 'Ministère des Forces armées');
INSERT INTO public.ref_entite (ref_id, code, libelle) VALUES (4, 'MFB', 'Ministère des Finances et du Budget ');
INSERT INTO public.ref_entite (ref_id, code, libelle) VALUES (5, 'MGSMJ', 'Ministère de la justice , Garde des Sceaux');
INSERT INTO public.ref_entite (ref_id, code, libelle) VALUES (6, 'MAESE', 'Ministre des Affaires étrangères, et des Sénégalais de l Extérieur');
INSERT INTO public.ref_entite (ref_id, code, libelle) VALUES (7, 'MIN', 'Ministère de l Intérieur ');


--
-- TOC entry 2495 (class 0 OID 55652)
-- Dependencies: 184
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role (role_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (role_id, role) VALUES (2, 'ROLE_USER');
INSERT INTO public.role (role_id, role) VALUES (3, 'ROLE_ADMINISTRATEUR');
INSERT INTO public.role (role_id, role) VALUES (4, 'ROLE_CHEF_DE_POSTE');


--
-- TOC entry 2496 (class 0 OID 55657)
-- Dependencies: 185
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_role (user_id, role_id) VALUES (0, 2);
INSERT INTO public.user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO public.user_role (user_id, role_id) VALUES (4, 4);
INSERT INTO public.user_role (user_id, role_id) VALUES (52, 3);


--
-- TOC entry 2522 (class 0 OID 0)
-- Dependencies: 187
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 54, true);


--
-- TOC entry 2523 (class 0 OID 0)
-- Dependencies: 204
-- Name: refEntiteSeq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."refEntiteSeq"', 1, false);


--
-- TOC entry 2524 (class 0 OID 0)
-- Dependencies: 205
-- Name: seq_entitepublic; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_entitepublic', 7, true);


-- Completed on 2022-09-10 18:08:58 CEST

--
-- PostgreSQL database dump complete
--
