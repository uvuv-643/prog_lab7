INSERT INTO persons (
    coordinates_id,
    height,
    weight,
    color,
    country_id,
    location_id,
    user_id
) VALUES (
    ?,
    ?,
    ?,
    ?,
    (SELECT id FROM countries WHERE countries.country = ?),
    ?,
    ?
)