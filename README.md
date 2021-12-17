# Supported operations
## User /api/v1/user
### POST
* Create - Required fields {first_name, last_name, email, password, user_type (USER/OWNER/COLLABORATOR), country_name, state_name, city_name}
### PUT
* Update /{id} - Any of the fields above except email.
### DELETE
* Delete /{id} (needs fix - foreign key issues)
### GET
* Get by ID /{id}
* Get all
* Get all votes from user /{id}/votes
* Get all users created after date /user?date=yyyy-mm-dd
* Get all users from country/state/city /user?=countryName=country&stateName=state&cityName=city. Exclude state a city are optional.

## Startup /api/v1/startup
### POST
* Create - Required fields {name, description, body, goal, published, id_owner} Optional {"tags":[{"tag1":"tag", {"tag2":"tag"}], "images_url"[{"url": "url", "description":"desc"]
* Add startup to event /{startupId}. In request body send {"name" = "event_name"}.
### PUT
* Update /{id}
### DELETE
* Delete  /{id} (needs fix)
### GET
* Get all
* Get all published /startup?=published=true
* Get by tag /startup?tag=science

Note: when getting all startups, the amount of votes showed does not count towards ANY current or future events. Votes earned in events do not show in here (check Event operations).

## Event api/v1/event
### POST
* Create - Required fields {name, description, end_date (LocalDateTime), "prizepool"}
* Add startup /{eventId}/{startupId}
### PUT
* Update /{id}
### DELETE
* Delete /{id} (needs fix)
## GET
* Get all
* Get by ID /{id}
Note: getting the event by ID will return its information and a list of startups registered in the event. This list is sorted (DESC) by the amount of votes each startup has earned in the event.

## Vote /api/v1/vote
### POST
* Create - Required fields {"user_id": "id", "startup_id": "id", "event_id": "id", "platform": "WEB/SERVICE/MOBILE"}.
Note: "event_id" is optional. When not included, votes are added to a list that does not count towards any future events. All startups start with 0 votes when joining an event.

Check postman collection.

## TODO
* Fix delete
* Unit testing
