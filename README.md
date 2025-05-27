# Getting Started

## Docker
- Execute the below command to start the service
```
export $(cat .local.env | xargs) 

---------- or ----------

export $(cat .prod.env | xargs)

docker-compose build

docker-compose up -d
```

## Swagger
http://localhost:9090/api/swagger-ui/index.html

## Health
http://localhost:9091/actuator/health