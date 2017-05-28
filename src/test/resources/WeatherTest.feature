Feature: This is the test suite for weather forecast service
  Scenario: Test LON and LAT values
    When We are requesting weather info
    Then LON should be -0.13
    And LAT should be 51.51