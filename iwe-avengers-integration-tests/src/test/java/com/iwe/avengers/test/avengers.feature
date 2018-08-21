Feature: Perform integrated tests on the Avengers registration API

Background:
* url 'https://4uqlp89ak2.execute-api.us-east-1.amazonaws.com/dev'

#Scenario: Get Avenger by id
#
#Given path 'avengers', 'uigv-hxbj-plsd-qwei'
#When method get
#Then status 200
#And match response == {id:'#string', name:'Iron Man', secretIdentity:'Tony Stark'}

Scenario: Access endpoint without authorization token

Given path 'avengers', 'anyid'
When method get
Then status 401

Scenario: Avenger not found

Given path 'avengers', 'invalid'
When method get
Then status 404

Scenario: Registry a new Avenger

# Create the avenger
Given path 'avengers'
And request {name:'Doutor Estranho', secretIdentity: 'Stephen Strange'} # data to request
When method post
Then status 201 # 'Created' status
And  match response == {id:'#string', name:'Doutor Estranho', secretIdentity: 'Stephen Strange'}

# Get the saved avenger by id
* def savedAvenger = response

#Confirm the registration
Given path 'avengers', savedAvenger.id
When method get
Then status 200
And match $ == savedAvenger

Scenario: Registry a new Avenger with invalid payload

Given path 'avengers'
And request {secretIdentity: 'Steve Rogers'} # data to request
When method post
Then status 400 # 'Bad request' status

Scenario: Update a Avenger

# Create a new avenger
Given path 'avengers'
And request {name:'Feiticeira Escarlate', secretIdentity: 'Wanda'}
When method post
Then status 201 # 'Created' status

* def avengerToUpdate = response

# Update the created avenger
Given path 'avengers', avengerToUpdate.id
And request {name:'Feiticeira Escarlate <3', secretIdentity: 'Wanda Maximoff'}
When method put
Then status 200
And match $.id == avengerToUpdate.id
And match $.name == 'Feiticeira Escarlate <3'
And match $.secretIdentity == 'Wanda Maximoff'

* def updatedAvenger = response

# Confirm the update
Given path 'avengers', avengerToUpdate.id
When method get
Then status 200
And match $ == updatedAvenger

Scenario: Update a Avenger with invalid payload

# Create a new avenger
Given path 'avengers'
And request {name:'Mercurio', secretIdentity: 'Pietro Maximoff'}
When method post
Then status 201 # 'Created' status

* def avengerToUpdate = response

#Try update the created avenger with only one attribute
Given path 'avengers',  avengerToUpdate.id
And request {secretIdentity: 'Merc'}
When method put
Then status 400

Scenario: Delete Avenger by id

# Create a new avenger
Given path 'avengers'
And request {name:'Soldado Invernal', secretIdentity: 'Bucky Barnes'}
When method post
Then status 201 # 'Created' status

* def avengerToDelete = response

# Delete the created avenger
Given path 'avengers', avengerToDelete.id
When method delete
Then status 204

# Confirm exclusion
Given path 'avengers', avengerToDelete.id
When method get
Then status 404

Scenario: Delete by id with a non-existent Avenger

# Delete a avenger
Given path 'avengers', 'invalid'
When method delete
Then status 404
