BEGIN;
INSERT INTO entity (ent_id, locale_id, created_at, current_revision, creator) 
VALUES 
(9,1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(9,1, 'First Product', '2010-08-18', 1);

INSERT INTO product (prod_id) VALUES (9);

INSERT INTO productrevision (prod_id, rev,  imageurl)
VALUES
(9,1,'nix.png');



/* INSERT PROPERTIES*/
/* ***** Prop 1 ***** */
INSERT INTO entity (ent_id, locale_id, created_at, current_revision, creator) 
VALUES 
(10,1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(10,1, 'energy', '2010-08-18', 1);

INSERT INTO property (prop_id) VALUES (10);

INSERT INTO entityproperty (eprop_id, prop_id, ent_id, value, min_rev)
VALUES
(1, 10, 9, '20', 1);

INSERT INTO propertyrevision (prop_id, rev, name, minvalue, maxvalue, uniq)
VALUES
(10,1, 'energy',0,10,true);


/* ***** Prop 2 ***** */
INSERT INTO entity (ent_id, locale_id, created_at, current_revision, creator) 
VALUES 
(11,1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(11,1, 'kmh', '2010-08-18', 1);

INSERT INTO property (prop_id) VALUES (11);

INSERT INTO entityproperty (eprop_id, prop_id, ent_id, value, min_rev)
VALUES
(2, 11, 9, '180', 1);

INSERT INTO propertyrevision (prop_id, rev, name, minvalue, maxvalue, uniq)
VALUES
(11,1, 'kmh',0,10,true);

/* ************** */
COMMIT;