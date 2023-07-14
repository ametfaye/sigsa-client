-- ROLES
INSERT INTO public.role(role_id, role, description, libelle) VALUES (1, 'ADMIN', 'Administration de la plateforme', 'Administrateur');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (2, 'EDIODM', 'Initialisation d''une mission', 'Initialisation d''une mission');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (3, 'UPODM', 'Modifier ordre de mission', 'Modifier ordre de mission');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (4, 'ADPART', 'Ajouter/Supprimer des participant', 'Ajouter/Supprimer des participant');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (5, 'TRODM', 'Transmettre au supérieur hiérarchique', 'Transmettre au supérieur hiérarchique');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (6, 'ERAODM', 'Saisir rapport de mission', 'Saisir rapport de mission');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (7, 'GFODM', 'Gestion des frais de mission', 'Gestion des frais de mission');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (8, 'VRCHS', 'Validation/Refus mission (chef de service)', 'Validation/Refus mission (chef de service)');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (9, 'ARSG', 'Acceptation/Refus mission nationale (SG)', 'Acceptation/Refus mission nationale (SG)');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (10, 'ARSGG', 'Acceptation/Refus mission internationale (SGG)', 'Acceptation/Refus mission internationale (SGG)');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (11, 'VRSGG', 'Validation/Refus Sécrétaire général du gouvernement', 'Validation/Refus Sécrétaire général du gouvernement');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (12, 'GENODM', 'Générer ordre de mission', 'Générer ordre de mission');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (13, 'GENAST', 'Générer autorisation de sortie de territoire', 'Générer autorisation de sortie de territoire');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (14, 'SVDOC', 'Scanner/Vérifier document ODM et AST', 'Scanner/Verifier document ODM et AST');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (15, 'PUBODM', 'Publications mission', 'Publications mission');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (16, 'EASTMAIL', 'Envoyer AST par mail (SG)', 'Envoyer AST par mail (SG)');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (17, 'EODMMAIL', 'Envoyer ODM par mail (SG)', 'Envoyer ODM par mail (SG)');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (18, 'TBADM', 'Tableau de bord admin', 'Tableau de bord admin');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (19, 'TBCHS', 'Tableau de bord chef de service', 'Tableau de bord chef de service');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (20, 'TBSG', 'Tableau de bord SG', 'Tableau de bord SG');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (21, 'TBSGG', 'Tableau de bord SGG', 'Tableau de bord SGG');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (22, 'TBGS', 'Tableau de bord gestionnaire de service', 'Tableau de bord gestionnaire de service');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (23, 'SIGNAST', 'Signer les autorisations de sortie', 'Signer les autorisations de sortie');
INSERT INTO public.role(role_id, role, description, libelle) VALUES (24, 'SIGNODM', 'Signer les ordres de mission','Signer les ordres de mission');
	
-- ENTITITE PUBLIC 
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (1, 'PR',  'Présidence de la République');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (100, 'PRIM',  'Primature');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (2, 'AN',  'Assemblée nationale');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (3, 'CCCT',  'Conseil constitutionnel, Cour suprême, Cour des Comptes, Cours et Tribunaux');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (4, 'CESE',  'Conseil Economique, Social et Environnemental');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (5, 'HCCT',  'Haut Conseil des Collectivités Territoriales');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (6, 'MFA',  'Ministère des Forces Armées');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (7, 'GSMJ' , 'Garde des Sceaux, ministre de la Justice');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (8, 'MAESE' , 'Ministère des Affaires étrangères et des Sénégalais de l’extérieur');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (9, 'MI' , 'Ministère de l’Intérieur');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (10, 'MFB' , 'Ministère des Finances et du Budget');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (11, 'MITTD' , 'Ministère des Infrastructures, des Transports terrestres et du Désenclavement');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (12, 'MAERSA' , 'Ministère de l’Agriculture, de l’Equipement rural et de la Souveraineté alimentaire');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (13, 'MEPC' , 'Ministère de l’Economie, du Plan et de la Coopération');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (14, 'MEA' , 'Ministère de l’Education nationale');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (15, 'MESRI' , 'Ministère de l’Enseignement supérieur, de la Recherche et de l’Innovation');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (16, 'MFPAI' , 'Ministère de la Formation professionnelle, de l’Apprentissage et de l’Insertion');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (17, 'MEA' , 'Ministère  de l’Eau et de l’Assainissement');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (18, 'MFFPE' , 'Ministère de la Femme, de la Famille et de la Protection des enfants');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (19, 'MSAS' , 'Ministère  de la Santé et de l’Action sociale');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (20, 'MMG' , 'Ministère des Mines et de la Géologie');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (21, 'MPE' , 'Ministère du Pétrole et des Energies');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (22, 'MTADIA' , 'Ministère des Transports aériens et du Développement des infrastructures aéroportuaires');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (23, 'MEDDTE' , 'Ministère de l’Environnement du Développement durable et de la Transition écologique');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (24, 'MPEM' , 'Ministère des Pêches et de l’Économie maritime');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (25, 'MDSRI' , 'Ministère du Dialogue social et des Relations avec les institutions');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (26, 'MULHP' , 'Ministère de l’Urbanisme, du Logement et de l’Hygiène publique');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (27, 'MCCPMEPG' , 'Ministère du Commerce, de la Consommation et des Petites et moyennes entreprises, porte-parole du gouvernement');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (28, 'MDIPMI' , 'Ministère du Développement industriel et des Petites et moyennes industries');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (29, 'MDICSNEST' , 'Ministère du Développement communautaire, de la Solidarité nationale et de l’Equipé sociale et territoriale');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (30, 'MMESS' , 'Ministère de la Microfinance, de l’Economie sociale et solidaire');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (31, 'MCTADT' , 'Ministère des Collectivités territoriales, de l’Aménagement et du Développement des territoires');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (32, 'MJEE' , 'Ministère de la Jeunesse, de l’Entrepreneuriat et de l’Emploi');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (33, 'MS' , 'Ministère des Sports');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (34, 'MTL' , 'Ministère du Tourisme et des Loisirs');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (35, 'MCPH' , 'Ministère de la Culture et du Patrimoine historique');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (36, 'MCTEN' , 'Ministère de la Communication, des Télécommunications et de l’Economie numérique');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (37, 'MFPETSP' , 'Ministère de la Fonction publique et de la Transformation du secteur public');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (38, 'MATSI' , 'Ministère de l’Artisanat et de la Transformation du secteur informel');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (39, 'MEPA' , 'Ministère de l’Elevage et des Productions animales');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (40, 'MMAESECSE' , 'Ministère auprès du ministre des Affaires étrangères et des Sénégalais de l’extérieur, chargée des Sénégalais de l’extérieur');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (41, 'MGSMJBGPD' , 'Ministère  auprès du garde des Sceaux, ministre de la Justice, chargé de la Bonne gouvernance de la Promotion des droits humains');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (42, 'MISPPC' , 'Ministère auprès du ministre de l’Intérieur, chargé de la Sécurité de proximité et de la Protection civile');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (43, 'MMEAPGI' , 'Ministère auprès du ministre de l’Eau et de l’Assainissement, chargé de la Prévention et de la Gestion des inondations.');
INSERT INTO public.ref_entite( ref_id, code, libelle) VALUES (44, 'HCDS',  'Haut Conseil du Dialogue Social');

--- Grades
INSERT INTO public.grade(g_id, code, libelle) VALUES (1 , '0PCECE', 'Principal de classe exceptionnelle Classe exceptionnelle');
INSERT INTO public.grade(g_id, code, libelle) VALUES (2 , '0PCECE', 'Première Classe (1re)  - 2e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (3 , '0PCECE', 'Première Classe (1re)  - 1e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (4 , '0PCECE', 'Deuxième (2e) Classe - 2e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (5 , '0PCECE', 'Deuxième (2e) Classe - 1e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (6 , '0PCECE', 'Troixième (3e) Classe - 2e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (7 , '0PCECE', 'Troixième (3e) Classe - 1e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (8 , '0PCECE', 'Quatrième (4e) Classe - 2e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (9 , '0PCECE', 'Quatrième (4e) Classe - 1e échelon');
INSERT INTO public.grade(g_id, code, libelle) VALUES (10 , '9STA', 'Stagiaire');

--- Fonction
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (1, '0SF', 'Sans fonction', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (2, '1PPCS', 'Premier Président de la Cour suprême', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (3, '1PGS', 'Procureur Général près la Cour suprême', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (4, 'PCCS', 'Président de Chambre à la Cour suprême	3', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (5, 'PGPC', 'Procureur général près une Cour d’appel	4', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (6, 'M', 'Ministre', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (7, '7S', 'Sécrétaire', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (8, '0SF', 'Sécrétaire Gouvernement', 1);
INSERT INTO public.fonction( id, code, libelle, g_id) VALUES (9, '0SF', 'Consultant', 1);

--- Service
INSERT INTO public.services(srv_id, code, libelle, ref_id) VALUES (1, 'SRV_INFORMATIQUE', 'Service informatique', 9);
INSERT INTO public.services(srv_id, code, libelle, ref_id) VALUES (2, 'SRV_MARCHE_PUBLIC', 'Service des marchés publics', 9);
INSERT INTO public.services(srv_id, code, libelle, ref_id) VALUES (3, 'SRV_JURIDIQUE', 'Service juridique', 9);
INSERT INTO public.services(srv_id, code, libelle, ref_id) VALUES (4, 'SRV_SPORT', 'Service sport', 33);
INSERT INTO public.services(srv_id, code, libelle, ref_id) VALUES (5, 'SRV_BUDGET', 'Service finance', 10);

--Sous Service
INSERT INTO public.sous_services(ssrv_id, code, libelle, srv_id) VALUES (1, 'SSRV_INFO', 'Sous Service informatique', 1);

--- Groupe
INSERT INTO public.groupe(gr_id, code, libelle) VALUES (1, 'GA', 'I');
INSERT INTO public.groupe(gr_id, code, libelle) VALUES (2, 'GA', 'II');
INSERT INTO public.groupe(gr_id, code, libelle) VALUES (3, '1G', 'A3');
INSERT INTO public.groupe(gr_id, code, libelle) VALUES (4, '2F', 'F');
INSERT INTO public.groupe(gr_id, code, libelle) VALUES (5, 'CF', 'C');

--- etat ODM
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (1, 'EA', 'Enregistré');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (2, 'TCP', 'Transmis au chef de service');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (3, 'AD', 'Accepté par le chef de service');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (4, 'RD', 'Refusé par le chef de service');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (5, 'TSG', 'Transmis au SG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (6, 'SSG', 'Signé par le SG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (7, 'ASG', 'Accepté par le SG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (8, 'RSG', 'Refusé par le SG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (9, 'TSGG', 'Transmis au SGG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (10, 'ASGG', 'Accepté par le SGG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (11, 'RSGG', 'Refusé par le SGG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (12, 'SSGG', 'Signé par le SGG');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (13, 'BE', 'Billets édités');
INSERT INTO public.etat_ordre_de_mission( eodm_id, code_etat, libelle_etat) VALUES (14, 'ECH', 'ECHUE');

-- DEFAULT USER
INSERT INTO public.personne_physique(pph_id, nom, prenom, sexe, email, telephone) VALUES (1, 'admin', 'admin', 'M', 'ametfaye@gmail.com', '123456789');
INSERT INTO public.agent(a_id, deleted, matricule, f_id, gr_id, pph_id, ssrv_id) VALUES (1, 0, 'admin', 1, 1, 1, 1);
INSERT INTO public.user_infos(user_id, active, date_creation, deleted, identifiant, password, a_id) VALUES (1, 1, now(), 0, 'admin', '$2a$10$EDA7ww8g5g5HvbKuhVjqAuliY5emeus/RY1z6QwuM/nS9yLOLkJX6', 1);
INSERT INTO public.user_role(user_id, role_id ) VALUES (1, 1);
INSERT INTO public.user_role(user_id, role_id ) VALUES (1, 18);

--- Type document
INSERT INTO public.type_document( td_id, code_doc, libelle_doc) VALUES (1, 'RAPPORT_DE_MISSION', 'Rapport de mission');
INSERT INTO public.type_document( td_id, code_doc, libelle_doc) VALUES (2, 'ANNEXES_RAPPORT', 'Annexes rapport');
INSERT INTO public.type_document( td_id, code_doc, libelle_doc) VALUES (3, 'ODM', 'Ordre de mission');
INSERT INTO public.type_document( td_id, code_doc, libelle_doc) VALUES (4, 'AST', 'Autorisation de sortie');
INSERT INTO public.type_document( td_id, code_doc, libelle_doc) VALUES (5, 'TDR', 'Termes de référence');

--- Statut document
INSERT INTO public.statut_document( stdoc_id, code, libelle) VALUES (1, 'GENERE', 'Généré');
INSERT INTO public.statut_document( stdoc_id, code, libelle) VALUES (2, 'SIGNE_ELEC', 'Signé électroniquement');
INSERT INTO public.statut_document( stdoc_id, code, libelle) VALUES (3, 'SIGNE_MANU', 'Signé manuellement');

 --- Type evenement  
INSERT INTO public.type_evenement VALUES (1, 'CM', 'Création de la mission');
INSERT INTO public.type_evenement VALUES (2, 'AM', 'Acceptation de la mission par le chef de service');
INSERT INTO public.type_evenement VALUES (3, 'RM', 'Refus de la mission par le chef de service');
INSERT INTO public.type_evenement VALUES (4, 'AMSGG', 'Acceptation mission par le SGG');
INSERT INTO public.type_evenement VALUES (5, 'RMSGG', 'Refus Mission par le SGG');
INSERT INTO public.type_evenement VALUES (6, 'AP', 'Ajout de participant');
INSERT INTO public.type_evenement VALUES (7, 'SP', 'Suppression de participant');
INSERT INTO public.type_evenement VALUES (8, 'ST', 'Sortie de territoire');
INSERT INTO public.type_evenement VALUES (9, 'ET', 'Entrée de territoire');
INSERT INTO public.type_evenement VALUES (10, 'PM', 'Publication de mission');
INSERT INTO public.type_evenement VALUES (11, 'TCS', 'Transmis au chef de service');
INSERT INTO public.type_evenement VALUES (12, 'TSGG', 'Transmis au SGG');
INSERT INTO public.type_evenement VALUES (13, 'TSG', 'Transmis au SG');
INSERT INTO public.type_evenement VALUES (14, 'SMSG', 'Signature de la mission par le SG');
INSERT INTO public.type_evenement VALUES (15, 'RMSG', 'Refus de la mission par le SG');
INSERT INTO public.type_evenement VALUES (16, 'AMSG', 'Acceptation de la mission par le SG');
INSERT INTO public.type_evenement VALUES (17, 'GODM', 'Génération ODM');
INSERT INTO public.type_evenement VALUES (18, 'GAST', 'Génération AST');
INSERT INTO public.type_evenement VALUES (19, 'SODM', 'Signature ODM');
INSERT INTO public.type_evenement VALUES (20, 'SAST', 'Signature AST');

--- Ampliations
INSERT INTO public.ampliation(id, code, libelle) VALUES (1, 'MEF', 'MEF');
INSERT INTO public.ampliation(id, code, libelle) VALUES (2, 'MEFMDB', 'MEF/MDB');
INSERT INTO public.ampliation(id, code, libelle) VALUES (3, 'MEFSG', 'MEF/SG');
INSERT INTO public.ampliation(id, code, libelle) VALUES (4, 'MEFIGF', 'MEF/IGF');
INSERT INTO public.ampliation(id, code, libelle) VALUES (5, 'MEFDTAI', 'MEF/DTAI');
INSERT INTO public.ampliation(id, code, libelle) VALUES (6, 'MEFDAGEPERS', 'MEF/DAGE/PERS');
INSERT INTO public.ampliation(id, code, libelle) VALUES (7, 'INTERESSE', 'INTÉRESSÉ');
INSERT INTO public.ampliation(id, code, libelle) VALUES (8, 'ARCHIVES', 'ARCHIVES');

--- Motif refus
INSERT INTO public.motif_refus(mf_id, code, libelle) VALUES (1, '01', 'Les TDR de la mission sont incomplètes');
INSERT INTO public.motif_refus(mf_id, code, libelle) VALUES (2, '02', 'Le budget de la mission est élevé');

--- pays
INSERT INTO pays(pays_id, libelle, code_pays, continent) VALUES (1, 'Sénégal' , 'SN', 'Afrique' );  
INSERT INTO pays(pays_id, libelle, code_pays, continent) VALUES (2, 'France' , 'FRA', 'Europe' );  
INSERT INTO pays(pays_id, libelle, code_pays, continent) VALUES (3, 'Italy' , 'ITA', 'Europe' );
INSERT INTO pays(pays_id, libelle, code_pays, continent) VALUES (4, 'Belgium' , 'BEL', 'Europe' ); 

--- regions
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (1 , 'Dakar', 1 ,'14.78418','-17.32701');
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (2 , 'Diourbel', 1 ,'15.13953','-12.78577');
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (3 , 'Fatick', 1 ,'13.68333','-16.41667');
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (4 , 'Kaolack', 1 ,'14','-16');
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (5 , 'Paris', 2 ,'13.33333','-14.61667');
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (6 , 'Milan', 3 ,'14.63333','-15.35');
INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) VALUES (7 , 'Bruxelles', 4 ,'16.2367','-13.97621');

--- destinations
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (1, '1DK', 'Dakar', -17.33, 14.78, 1);
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (2, '2DL', 'Diourbel', -12.79, 15.14, 2);
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (3, '3FK', 'Fatick', -16.42, 13.68, 3);
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (4, '4KL', 'Kaolack', -16.00, 14.00, 4);
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (5, 'PRS', 'Paris', 48.86, 2.35, 5);
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (6, 'ML', 'Milan', 45.47, 9.19, 6);
INSERT INTO public.destination(dst_id, dest_code, destination_libelle, latitude, longitude, regn_id) VALUES (7, 'BL', 'Bruxelles', 50.85, 4.35, 7);

--- insert module applicatif
INSERT INTO module_applicatif(id, mda_code, mda_libelle, mda_si_actif) VALUES (1, 'SMTPT', 'SMTPT TRANSACTIONNEL', 1);
INSERT INTO module_applicatif(id, mda_code, mda_libelle, mda_si_actif) VALUES (2, 'ODM', 'ORDRE DE MISSION', 1);

--- insert parametre_module sendinblue
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (1, 1, 'IDENTIFIANT', 'NUMERO DE CLIENT CHEZ MESSAGE BUSINESS', 'samersboui2016@gmail.com');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (2, 1, 'MOT_DE_PASSE', 'CLE D''ACCES AU SERVICE MESSAGE BUSINESS', 'qpnWT9xUQVvyLS0a');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (3, 1, 'HOST', 'HOTE SMTP', 'smtp-relay.sendinblue.com');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (4, 1, 'PORT', 'PORT SMTP', '587');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (5, 1, 'AUTH', 'SI BESOIN D''AUTHENTIFICATION', 'TRUE');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (6, 1, 'ENABLESTARTTLS', 'SI START TLS DOIT ETRE ACTIVE', 'TRUE');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (7, 1, 'EXPEDITEUR', 'ADRESSE EMAIL DE L''EXPEDITEUR', 'test@sigma.com');
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (8, 1, 'REPLY_TO', 'ADRESSE EMAIL POUR LE REPLY TO', 'test@sigma.com');

Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (9, 2, 'MAX_DUREE_MISSION', 'DUREE MAXIMUM D''UNE MISSION', '21');
   
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (10, 2, 'MAX_DELAI_EDITION', 'DELAI MAXIMUM D''UNE MISSION', '10');
   
Insert into PARAMETRE_MODULE
   (ID, module_applicatif_id, PAM_CODE, PAM_LIBELLE, PAM_CHAINE_VALEUR)
 Values
   (11, 2, 'MAX_PARTICIPANT', 'MAX PARTICIPANT', '10');
