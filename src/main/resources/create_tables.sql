drop table if exists urls;

create table urls (
	id int primary key,
	short_form text,
	long_form text
);
