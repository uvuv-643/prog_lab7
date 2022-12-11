SELECT
    persons.id as id,
    coordinates.x as x,
    coordinates.y as y,
    height,
    weight,
    color,
    countries.country as country,
    locations.x as location_x,
    locations.y as location_y,
    locations.title as location_title
FROM persons
INNER JOIN coordinates ON coordinates.id = persons.coordinates_id
LEFT JOIN locations ON locations.id = persons.location_id
LEFT JOIN countries ON countries.id = persons.country_id