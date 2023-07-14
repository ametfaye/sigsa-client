INSERT INTO parametrages (params_id, contexte, cron_expression, directoryftp, indice_majore, portftp, serveurftp, userftp, userftppass) VALUES
(0,'TEST',  '* *  * * * 1', '/home/ftp/depotCGOS' , '4.686', '22', '10.125.12.12', 'root', '');


------------------------------  ADRESSE & CONTACT ---------------------------------------------------------------------------------
------ADRESSE & CONTACT---------
-----------------------------------------------------------------------------------------------------------------------------------
--PERCEPTION DAKAR
INSERT INTO adrs_adresse(adrs_id, codepostal, libelle, quartier, ville, regn_id, departement_id, commune_id) 
	VALUES (1, 99999, 'Boulevard Djilly Mbaye', 'Perception de Dakar Centre', 'DAKAR', 1, 1, 2);
INSERT INTO cont_contact(cont_id, libelle, mobile, fixe, courriel, fax,   site_web) 
	VALUES (1, 'Perception de Dakar Centre', '33 822 50 30', '33 822 50 30', 'contact@servicepublic.sn', '33 822 50 30', 'www.servicepublic.sn');
	
------------------------------  FIN ADRESSE & CONTACT ------------------------------------------------------------------------------
------ADRESSE & CONTACT---------
------------------------------------------------------------------------------------------------------------------------------------



------------------------------  Perception ---------------------------------------------------------------------------------
------PERCEPTION---------
-----------------------------------------------------------------------------------------------------------------------------------
--PERCEPTION DAKAR
INSERT INTO poste_comptable (poste_compable_id, libelle, seuil_alerte_versement , adrs_id , cont_id) VALUES
(1, 'Trésorerie générale', 800000000, 1, 1),
(2, 'Paierie générale du Trésor', 500000000, 1, 1),
(3, 'Recette générale du Trésor', 100000000, 1, 1),
(4, 'Trésorerie Paierie pour l’Etranger', 15000000, 1, 1),
(5, 'Agence comptable des grands Projets', 15000000, 1, 1),
(45, 'Perception de Dakar-Port', 120000000, 1, 1),
(46, 'Perception de l aéroport international Blaise DIAGNE', 120000000, 1, 1),
(47, 'Recette Perception municipale de Dakar', 10000000, 1, 1),
(48, 'Perception de Pikine', 10000000, 1, 1),
(49, 'Recette Perception de Dakar Plateau', 10000000, 1, 1),
(50, 'Recette Perception de Dakar Bourguiba', 10000000, 1, 1),


-- Kaolack 8 9 
(6, 'Trésorerie Paierie régionale de Kaolack', 12000000, 1, 1),
(7, 'Recette Perception municipale de Thiès', 10000000, 1, 1),

-- Diourbel
(10, 'Trésorerie paierie régionale de Diourbel', 10000000, 1, 1),
(11, 'Recette perception municipale de Diourbel', 10000000, 1, 1),
(12, 'Perception de Mbacké', 8000000, 1, 1),
(13, 'Perception de Bambey', 8000000, 1, 1),

-- Fatick
(14, 'Trésorerie paierie régionale de Fatick', 10000000, 1, 1),
(15, 'Perception de Foundiougne', 8000000, 1, 1),
(16, 'Perception de Gossas', 8000000, 1, 1),

-- Fatick

(17, 'Trésorerie paierie régionale de Kaolack', 10000000, 1, 1),
(18, 'Recette perception municipale de  Kaolack', 10000000, 1, 1),

-- Kaffrine
(19, 'Perception de Kaffrine', 8000000, 1, 1),
(20, 'Perception de Nioro du Rip', 8000000, 1, 1),

-- Kolda
(21, 'Trésorerie paierie régionale de Kolda', 10000000, 1, 1),
(22, 'Perception de Vélingara', 8000000, 1, 1),
(23, 'Perception de Sédhiou', 8000000, 1, 1),

--  LOUGA
(24, 'Trésorerie paierie régionale de Louga', 10000000, 1, 1),
(25, 'Recette perception municipal de Louga', 10000000, 1, 1),
(26, 'Perception de Kébémer', 8000000, 1, 1),
(27, 'Perception de Linguère', 10000000, 1, 1),

--  Région de Saint-Louis
(28, 'Recette Perception municipale de Saint-Louis', 10000000, 1, 1),
(29, 'Perception de Dagana', 8000000, 1, 1),
(30, 'Perception de Podor', 8000000, 1, 1),
(31, 'Perception de Linguère', 8000000, 1, 1),

--  Région de Matam
(32, 'Perception de Matam', 8000000, 1, 1),

--  Région de THIES 
(33, 'Trésorerie Paierie régionale de Thiès', 12000000, 1, 1),
(34, 'Recette perception municipal de Thiès', 10000000, 1, 1),
(35, 'Perception de Mbour', 10000000, 1, 1),
(36, 'Perception de Tivaouane', 8000000, 1, 1),

--  Région de Ziguinchor 
(37, 'Recette Perception municipale de Ziguinchor', 10000000, 1, 1),
(38, 'Perception de Bignona', 8000000, 1, 1),
(39, 'Perception de Oussouye', 8000000, 1, 1),
(40, 'Perception de Tivaouane', 10000000, 1, 1),


--  Région de Tambacounda 
(41, 'Trésorerie paierie régionale de Tambacounda', 10000000, 1, 1),
(42, 'Perception de Bakel', 8000000, 1, 1),


--  Région de Kédougou 
(43, 'Perception de Kédougou', 8000000, 1, 1);

------------------------------  Fin Perception  ------------------------------------------------------------------------------
------PERCEPTION---------
------------------------------------------------------------------------------------------------------------------------------------
	
	
	

INSERT INTO role (role_id, role) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_CHEF_DE_POSTE'),
(4, 'ROLE_CONSOLIDATEUR');



INSERT INTO user_infos (user_id, active, email, last_name, name, password , poste_compable_id) VALUES
(0, 1, 'administrateur', 'Cheikh ', 'Mbacké', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 1),

(1, 1, 'consolidateur', 'Abdoulaye ', 'SAMB', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 1),

(2, 1, 'agentTG', 'Saloum', 'Diop', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 1),
(3, 1, 'chefdeposteTG', 'Saloum', 'Diop', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 1),

(4, 1, 'agentMBACKE', 'Cheikh', 'Mbacké', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 12),
(5, 1, 'chefdeposteMBACKE', 'Cheikh', 'Mbacké', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 12),

(6, 1, 'agentKEDOUGOU', 'Pape Ossueynou', 'Ndiaye', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 43),
(7, 1, 'chefdeposteKEDOUGOU', 'Pape Ousseynou', 'Ndiaye', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 43),

(8, 1, 'agentETRANGER', 'Mouhamed', 'FAYE', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 4),
(9, 1, 'chefdeposteETRANGER', 'Mouhammed', 'FAYE', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 4),

(10, 1, 'agentPlateau', 'Ibrahima', 'FAYE', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 49),
(11, 1, 'chefdepostePlateau', 'Ibrahima', 'FAYE', '$2a$10$wza7zXoKNO765bJGi8UGR.0.N9AW3rOX5/GK9ctNZqLHR3z3w5M.u' , 49);

INSERT INTO user_role (user_id, role_id) VALUES
(0, 1),
(1, 4),

(2, 2),
(3, 3),

(4, 2),
(5, 3),

(6, 2),
(7, 3),

(8, 2),
(9, 3),

(10, 2),
(11, 3);