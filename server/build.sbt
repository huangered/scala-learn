//lazy val root = (project in file("."))

lazy val hello_world = project in file("hello-world")

lazy val core = project in file("core")

lazy val spark = project in file("spark")

lazy val lib_raft = project in file("raft")

lazy val lib_paxos = project in file("paxos")