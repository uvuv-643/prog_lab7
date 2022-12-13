SELECT
    persons.id as id,
    persons.name as name,
    coordinates.x as coordinate_x,
    coordinates.y as coordinate_y,
    height,
    weight,
    color,
    country,
    (case when location_id IS NULL then false else true end) as is_location_present,
    locations.x as location_x,
    locations.y as location_y,
    created_at,
    locations.title as location_title,
    user_id,
    users.login as user_login
FROM persons
INNER JOIN coordinates ON coordinates.id = persons.coordinates_id
INNER JOIN users ON persons.user_id = users.id
LEFT JOIN locations ON locations.id = persons.location_id
ORDER BY person_order