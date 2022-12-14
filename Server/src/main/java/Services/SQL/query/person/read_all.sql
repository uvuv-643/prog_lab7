SELECT
    lab7_persons.id as id,
    lab7_persons.name as name,
    lab7_coordinates.x as coordinate_x,
    lab7_coordinates.y as coordinate_y,
    height,
    weight,
    color,
    country,
    (case when location_id IS NULL then false else true end) as is_location_present,
    lab7_locations.x as location_x,
    lab7_locations.y as location_y,
    created_at,
    lab7_locations.title as location_title,
    user_id,
    lab7_users.login as user_login
FROM lab7_persons
INNER JOIN lab7_coordinates ON lab7_coordinates.id = lab7_persons.coordinates_id
INNER JOIN lab7_users ON lab7_persons.user_id = lab7_users.id
LEFT JOIN lab7_locations ON lab7_locations.id = lab7_persons.location_id
ORDER BY person_order