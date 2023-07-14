

--- CASSER MDP EN LOCAL APRES  COPIE DE BASE 

UPDATE user_utilisateur
   SET
       motdepasse = 
		( SELECT   motdepasse FROM user_utilisateur where user_id = 6826)
		
		
		
		
		