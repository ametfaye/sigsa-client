CREATE INDEX odm_id_indx
    ON ordre_de_mission USING btree
    (odm_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
    
CREATE INDEX matricule_indx
    ON agent USING btree
    (matricule COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
