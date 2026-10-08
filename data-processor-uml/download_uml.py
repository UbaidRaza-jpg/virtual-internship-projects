import urllib.request
import zlib
import base64
import string

def encode_plantuml(text):
    zlibbed_str = zlib.compress(text.encode('utf-8'))
    compressed_string = zlibbed_str[2:-4]
    b64_str = base64.b64encode(compressed_string).decode('utf-8')
    b64_chars = string.ascii_uppercase + string.ascii_lowercase + string.digits + '+/'
    plantuml_chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_'
    trans = str.maketrans(b64_chars, plantuml_chars)
    return b64_str.translate(trans)

plantuml_code = """
@startuml
skinparam classAttributeIconSize 0
' Adjust layout to be narrower
left to right direction

note "already exists" as N1
note "already exists" as N2
note "already exists" as N3

class DataPoint
N1 .. DataPoint

enum ModeIdentifier
N2 .. ModeIdentifier

enum DatabaseIdentifier
N3 .. DatabaseIdentifier

class Processor {
  -currentMode : ProcessingMode
  -currentDatabase : Database
  +configure(mode: ModeIdentifier, database: DatabaseIdentifier): void
  +process(dataPoint: DataPoint): void
  -selectMode(mode: ModeIdentifier): ProcessingMode
  -selectDatabase(database: DatabaseIdentifier): Database
}

interface ProcessingMode <<interface>> {
  +handle(dataPoint: DataPoint, database: Database): void
}

interface Database <<interface>> {
  +connect(): void
  +insert(dataPoint: DataPoint): void
  +validate(dataPoint: DataPoint): boolean
}

class DumpMode {
  +handle(dataPoint: DataPoint, database: Database): void
}
class PassthroughMode {
  +handle(dataPoint: DataPoint, database: Database): void
}
class ValidateMode {
  +handle(dataPoint: DataPoint, database: Database): void
}

class PostgresDatabase {
  +connect(): void
  +insert(dataPoint: DataPoint): void
  +validate(dataPoint: DataPoint): boolean
}
class RedisDatabase {
  +connect(): void
  +insert(dataPoint: DataPoint): void
  +validate(dataPoint: DataPoint): boolean
}
class ElasticDatabase {
  +connect(): void
  +insert(dataPoint: DataPoint): void
  +validate(dataPoint: DataPoint): boolean
}

Processor "1" --> "1" ProcessingMode
Processor "1" --> "1" Database

ProcessingMode <|.. DumpMode
ProcessingMode <|.. PassthroughMode
ProcessingMode <|.. ValidateMode

Database <|.. PostgresDatabase
Database <|.. RedisDatabase
Database <|.. ElasticDatabase

Processor ..> ModeIdentifier
Processor ..> DatabaseIdentifier

ProcessingMode ..> DataPoint
Database ..> DataPoint
Processor ..> DataPoint
@enduml
"""

url = "http://www.plantuml.com/plantuml/pdf/" + encode_plantuml(plantuml_code)
print("Downloading PDF...")
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
with urllib.request.urlopen(req) as response, open('class-diagram.pdf', 'wb') as out_file:
    data = response.read()
    out_file.write(data)
print("Saved to class-diagram.pdf")
