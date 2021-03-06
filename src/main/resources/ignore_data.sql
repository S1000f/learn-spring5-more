-- noinspection SqlResolveForFile

delete from Taco_Order_Tacos;
delete from Taco_Ingredients;
delete from Taco;
delete from Taco_Order;

delete from Ingredient;
insert into Ingredient (id, name, type)
    values ( 'FLTO', 'Flour Tortilla', 'WRAP' );
insert into Ingredient (id, name, type)
values ( 'COTO', 'Corn Tortilla', 'WRAP' );
insert into Ingredient (id, name, type)
values ( 'GRBF', 'Ground Beef', 'PROTEIN' );
insert into Ingredient (id, name, type)
values ( 'CARN', 'Carnitas', 'PROTEIN' );
insert into Ingredient (id, name, type)
values ( 'TMTO', 'Cheddar', 'CHEESE' );
insert into Ingredient (id, name, type)
values ( 'LETC', 'Monterry Jack', 'CHEESE' );
insert into Ingredient (id, name, type)
values ( 'CHED', 'Diced Tomatoes', 'VEGGIES' );
insert into Ingredient (id, name, type)
values ( 'JACK', 'Lettuce', 'VEGGIES' );
insert into Ingredient (id, name, type)
values ( 'SLSA', 'Salsa', 'SAUCE' );
insert into Ingredient (id, name, type)
values ( 'SRCR', 'Sour Cream', 'SAUCE' );