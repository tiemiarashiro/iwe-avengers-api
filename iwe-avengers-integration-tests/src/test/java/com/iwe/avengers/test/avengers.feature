Feature: Perform integrated tests on the Avengers registration API

Background:
* url 'https://4uqlp89ak2.execute-api.us-east-1.amazonaws.com/dev'

Scenario: Get Avenger by Id

Given path 'avengers', 'uigv-hxbj-plsd-qwei'
When method get
Then status 200
And match response == {id:'#string', name:'Iron Man', secretIdentity:'Tony Stark'}


Scenario: Registry a new Avenger

Given path 'avengers'
And request {name:'Captain America', secretIdentity: 'Steve Rogers'} # data to request
When method post
Then status 201 # 'Created' status
And  match response == {id:'#string'}

Scenario: Delete a Avenger

Given path 'avengers', 'uigv-hxbj-plsd-qwei'
When method delete
Then status 204 # Success with no content status


Scenario: Update a Avenger

Given path 'avengers', 'uigv-hxbj-plsd-qwei'
And request {name:'Captain America <3', secretIdentity: 'Steve Rogers'}
When method put
Then status 200
And  match response == {name:'Captain America <3', secretIdentity: 'Steve Rogers'}

