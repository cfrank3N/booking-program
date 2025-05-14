# BE1 uppgift planering

---
<pre>
src/main/java/
├─ org/example/demo/
│   ├─ Repositories/
│   │  ├─ RoomRepository.java           ← extends JpaRepository
│   │  ├─ GuestRepository.java          ← extends JpaRepository
│   │  └─ BookingRepository.java        ← extends JpaRepository
│   │
│   ├─ Models/
│   │  ├─ Rooms.java                    ← @Entity class
│   │  ├─ Guest.java                    ← @Entity class
│   │  └─ Booking.java                  ← @Entity class
│   │
│   ├─ Controllers/ 
│   │  ├─ RoomController.java           ← @RestController
│   │  ├─ GuestController.java          ← @RestController
│   │  └─ BookingController.java        ← @RestController
│   │
│   ├─ Service/
│   │  └─ BookingService.java
│   │
│   │
│   ├─ Validation/
│   │  ├─                               ← create custom @Constraint interface. 
│   │  └─                               ← validator (implements @Constraint interface)
│   │   ─                               (@Email (jakarta)), (PhoneNum : @RegEx)
│   │   ─                                
│   │
│   └─ DatabaseSeeder.java              ← inserts initial data (for guest & rooms?)
│  
├─ org/example/demo/
│    └─ Resources/
│       ├─ Static/
│       ├─ Templates/                   ← HTML 
│       │   ├─ mainPage.html
│       │   ├─ checkIn.html
│       │   └─ bookingForm.html
│       │   
│       ├─ Application.properties       ← Main (spring.profiles.active=prod/dev)
│       ├─ Application-dev.properties   ← Inc testlogging, sout sens data OK (debug=true)
│       └─ Application-prod.properties  ← LaunchMode. No dataleaks (debug=false)
└─ test
    └─ java/
        └─ App/
            └─ AppTests/

- Datum, PW, booked(roomID-> Y/N), 
- (roomsize : (beds, maxNumGuests))
- 

</pre>

#### Use:
- Lombok
- Hibernate
- Spring JPA
- ORM (vs JDBC)
- Maven/Gradle?
- Thymeleaf
- Code First = classes first, hibernate creates columns where @Entity
- ResponseEntity!
- Logging slf4j

Room size. 
Enkelrum/DubbelRum1/DubbelRum2
Splitta på siza 