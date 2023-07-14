


INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1270, 'FK12704', 'KARANG', 9);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1950, 'KL1950', 'Ndieghenne Ndiba', 16);
INSERT INTO commune(commune_id, code, libelle, departement_id) VALUES (1951, 'KL1951', 'Thiare Alassane', 16);

INSERT INTO pgca_stock( stock_id, commune_id , code, libelle) VALUES (1270, 1270 ,'FK12704', 'KARANG');
INSERT INTO pgca_stock( stock_id, commune_id , code, libelle) VALUES (1950, 1950 ,'KL1950', 'Ndieghenne Ndiba');
INSERT INTO pgca_stock( stock_id, commune_id , code, libelle) VALUES (1951, 1951 ,'KL01951', 'Thiare Alassane');




INSERT INTO pgca_pointdevente(ptv_id, stock_id, commune_id , code, libelle) VALUES (1270, 1270 , 1270 ,  'FK1270', 'KARANG');
INSERT INTO pgca_pointdevente(ptv_id, stock_id, commune_id , code, libelle) VALUES (1950, 1950, 1950 , 'KL1950', 'Ndieghenne Ndiba');
INSERT INTO pgca_pointdevente(ptv_id, stock_id, commune_id , code, libelle) VALUES (1951, 1951 , 1951 , 'KL01951', 'Thiare Alassane');



-----  FIX DOUBLON MEP  : Doublon MEP , comptabilisé deux fois , on diminue la quantite : 21/06/2020

// Avant UPDATE : 184 , APRES UPDATE 110
select * from  public.pgca_intrant  where stock_id  = 115;  
update pgca_intrant set quantite = 110 where  produit_id = 9858;

select * from  public.pgca_bondelivraison b
inner join public.pgca_bondelivraisonproduit bp on b.bl_id = bp.bl_id  
where idstockreceptionnaire = 115;  