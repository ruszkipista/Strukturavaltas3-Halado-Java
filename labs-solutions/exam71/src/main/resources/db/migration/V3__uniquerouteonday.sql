alter table if exists routes 
    add constraint UniqueAirplanePerDay 
    unique (airplane_id, date_of_flight);