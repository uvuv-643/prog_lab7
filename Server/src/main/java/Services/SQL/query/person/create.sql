INSERT INTO lab7_persons (
    name,
    coordinates_id,
    height,
    weight,
    color,
    country,
    location_id,
    user_id
) VALUES (
    ?,
    ?,
    ?,
    ?,
    ?::color,
    ?::country,
    ?,
    ?
) RETURNING id