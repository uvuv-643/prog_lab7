UPDATE persons
SET person_order = (SELECT MAX(person_order) FROM persons) - person_order
WHERE user_id = ?