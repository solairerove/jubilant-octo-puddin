#!/usr/bin/python3.4

from py2neo import Graph

host_port = "localhost:7474"
user_name = "neo4j"
password = "woodstock"

graph = Graph("http://" + user_name + ":" + password + "@" + host_port + "/db/data/")

print(graph.run("MATCH (n) RETURN n LIMIT 200").dump())