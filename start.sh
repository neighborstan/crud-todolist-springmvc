#!/bin/bash

mvn clean package

docker compose down

docker compose up --build