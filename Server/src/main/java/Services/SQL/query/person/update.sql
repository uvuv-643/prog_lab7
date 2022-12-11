UPDATE persons SET
    coordinates_id = ?,
    height = ?,
    weight = ?,
    color = ?,
    country_id = (SELECT id FROM countries WHERE countries.country = ?),
    location_id = ?,
    user_id = ?
WHERE id = ?