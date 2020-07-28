DROP TABLE IF EXISTS night_club;

-- Create the night_club table
CREATE TABLE night_club (
  night_club_id INT AUTO_INCREMENT  IDENTITY(1,1) NOT NULL PRIMARY KEY,
  night_club_name VARCHAR(250) NOT NULL,
  quantity_Of_Visits VARCHAR(250) NOT NULL,
  status nchar (1) NOT NULL,

  created_user NVARCHAR(20),
  created_date DATETIME,
  last_update_user NVARCHAR(20),
  last_update_date DATETIME);





