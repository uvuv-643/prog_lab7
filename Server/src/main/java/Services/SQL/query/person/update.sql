UPDATE persons SET
    name = ?,
    coordinates_id = ?,
    height = ?,
    weight = ?,
    color = ?::color,
    country = ?::country,
    location_id = ?,
WHERE id = ?