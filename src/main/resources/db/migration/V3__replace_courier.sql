UPDATE courier_tool
SET role = 'ROLE_COURIER'
WHERE login = 'courier';

UPDATE courier_tool
SET password = '$2a$10$ESgUpPPrPva6iCoBaX5mBe5P8SlftThFaCdvMIch4J8OsXIH8XZB2'
WHERE login = 'courier';