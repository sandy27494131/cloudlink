#!/bin/bash

mvn -Dit.rabbit.hostname="rabbit" -Dit.rabbit.port="15672" -Dit.rabbit.username="guest" -Dit.rabbit.password="guest" \
    -Dit.rabbit2.hostname="hare" -Dit.rabbit2.port="5673" -Dit.rabbit2.username="guest" -Dit.rabbit2.password="guest" \
 verify