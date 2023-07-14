
/**** BUG des point de vente ajoutés mannuellment,  les Stock est a recuperer sur la MEPP*/
ALTER TABLE pgca_miseEnPlaceAeffectuee ADD ptv_id INTEGER NOT NULL REFERENCES pgca_pointdevente;
