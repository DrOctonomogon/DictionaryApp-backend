CREATE TABLE dictionary (
  id INTEGER PRIMARY KEY NOT NULL,
  entryTitle VARCHAR(50) NOT NULL,
  partOfSpeech VARCHAR(50) NOT NULL,
  definition VARCHAR (255) NOT NULL,
  etymology VARCHAR(50) NOT NULL
);

-- INSERT INTO dictionary
-- VALUES(1,'test', 'noun', 'This is a test.', 'idk, lol');