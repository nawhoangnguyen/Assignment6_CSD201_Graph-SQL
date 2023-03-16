CREATE DATABASE Assignment6
USE Assignment6

CREATE TABLE AdjacencyMatrix (
	vertex1 varchar(2),
	vertex2 varchar(2),
	vertex3 varchar(2),
	vertex4 varchar(2),
	vertex5 varchar(2),
	vertex6 varchar(2),
	vertex7 varchar(2),
	vertex8 varchar(2)
)



CREATE TABLE VertexName  (
	nameOfVertex varchar(2)
	
)

INSERT INTO	AdjacencyMatrix VALUES ('0',  '0',  '2'  ,'3' , '0'  ,'0'  ,'0'  ,'0')
INSERT INTO	AdjacencyMatrix VALUES ('0',  '0',  '4'  ,'5' , '0'  ,'0'  ,'6'  ,'7')
INSERT INTO	AdjacencyMatrix VALUES ('2',  '4',  '0'  ,'0' , '8'  ,'9'  ,'10'  ,'1')
INSERT INTO	AdjacencyMatrix VALUES ('3',  '5',  '0'  ,'0' , '0'  ,'11'  ,'0'  ,'12')
INSERT INTO	AdjacencyMatrix VALUES ('0',  '0',  '8'  ,'0' , '0'  ,'13'  ,'14'  ,'15')
INSERT INTO	AdjacencyMatrix VALUES ('0',  '0',  '9'  ,'11' , '13'  ,'0'  ,'16'  ,'0')
INSERT INTO	AdjacencyMatrix VALUES ('0',  '6',  '10'  ,'0' , '14'  ,'16'  ,'0'  ,'0')
INSERT INTO	AdjacencyMatrix VALUES ('0',  '7',  '1'  ,'12' , '15'  ,'0'  ,'0'  ,'0')


INSERT Into VertexName VALUES ('A')
INSERT Into VertexName VALUES ('B')
INSERT Into VertexName VALUES ('C')
INSERT Into VertexName VALUES ('D')
INSERT Into VertexName VALUES ('M')
INSERT Into VertexName VALUES ('N')
INSERT Into VertexName VALUES ('P')
INSERT Into VertexName VALUES ('Q')


SELECT * FROM VertexName
SELECT * FROM AdjacencyMatrix












