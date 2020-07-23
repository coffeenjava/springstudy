# springstudy

## Enum Converting
Enum 을 원하는 대로 적절하게 컨버팅하는 방법을 설명합니다.
- Enum 형태는 YesNo / User.Grade 로 확인 가능합니다.   
- 정의한 Enum을 공통적으로 처리할 필요가 있는 부분이 있어(CustomEnumType/YesNoConverter/StringToEnumConverterFactory)   
BaseEnum 인터페이스를 상속하였습니다.

### Converting 발생하는 부분 / 예시
#### Http Request -> Controller(input parameter)   
ex) "Y"(String) -> Yes(Enum)
#### Controller(return value) -> Http Response   
ex) Yes(Enum) -> "Y"(String)
#### JPA Entity -> DB   
ex) Yes(Enum) -> "Y"(String)
#### DB -> JPA Entity   
ex) "Y"(String) -> Yes(Enum)
   
### Converting 설정
#### Http Request -> Controller(input parameter)
@RequestBody 가 선언된 parameter 의 경우 jackson converter(MappingJacson2HttpMessageConverter)가 동작합니다.   
Enum 의 code 값에 @JsonValue 를 선언하여 deserializing 에 사용되도록 합니다.

#### Controller(return value) -> Http Response
이전의 선언(@JsonValue)으로 serializing 에도 적용되므로 따로 설정할 부분이 없습니다.

#### JPA Entity <-> DB
Entity 에 정의된 Enum 필드에 @Type 을 선언하여 CustomEnumType 을 통해 컨버팅될 수 있도록 합니다.(User 엔티티 참고)   
YesNo 처럼 여러 Entity 에서 자주 사용될 가능성이 있는 Enum 은 자동 컨버팅 될 수 있도록 javax.persistence.AttributeConverter 를 상속하여 컨버터를(YesNoConverter) 구현하는게 편리할 수 있습니다.

### 추가 정보
- @RequestParam 이 선언된 parameter 의 경우 jackson converter 가 동작하지 않고 컨테이너에 내장된 converter(Converter 혹은 ConverterFactory 구현 클래스)가 동작하므로, 우리가 정의한 Enum 에 대한 ConverterFactory 를 따로 구현하여 FormatterRegistry 에 등록해야 합니다.   
(StringToEnumConverterFactory/WebMvcConfig 소스 참고)
- Controller 의 return value 는 항상 jackson converter 가 동작합니다. 이유는 UserController 의 @RestController 설정으로 인해 모든 메서드에 @ResponseBody 가 적용되어 있기 때문입니다.
- 스프링 DispatcherServlet 프로세스
  1. 요청 정보를 통해 적절한 HandlerMapping 을 찾아서 HandlerExcutionChain 을 생성합니다.
      - HandlerMapping 은 HandlerExcutionChain 을 생성해주는게 주된 역할입니다.
        - 실제 주로 사용되는 HandlerMapping 인 RequestMappingHandlerMapping 은 요청의 http method 와 url 정보를 토대로 대상 Controller를 찾습니다.
      - HandlerExcutionChain 은 HandlerMethod + Interceptor 목록을 가지고 있습니다.
      - HandlerMethod 는 대상 Controller의 class/method/parameter 메타 정보 + 실행가능한 Controller Bean 을 가지고 있습니다.
  1. HandlerExcutionChain 의 HandlerMethod 를 통해 HandlerAdapter 를 선정합니다.
  1. HandlerExcutionChain 내부의 Interceptor 목록의 preHandler() 메서드를 각각 실행합니다.
  1. HandlerAdapter 를 통해 대상 Controller 실행시킵니다.
  1. HandlerExcutionChain 내부의 Interceptor 목록의 postHandler() 메서드를 각각 실행합니다.
  1. HandlerExcutionChain 내부의 Interceptor 목록의 afterCompletion() 메서드를 각각 실행합니다.
  
