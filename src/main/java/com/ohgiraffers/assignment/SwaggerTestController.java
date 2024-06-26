package com.ohgiraffers.assignment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Tag(name="숙제")
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {

    private List<MenuDTO> list;

    public  SwaggerTestController(){

        list = new ArrayList<>();

        list.add(new MenuDTO(1,"너구리",3500,LocalDate.now()));
        list.add(new MenuDTO(2,"신라면",3000,LocalDate.now()));
        list.add(new MenuDTO(3,"오징어짬뽕",3800,LocalDate.now()));
    }

    @Operation(summary = "메뉴정보 조회\", description = \"메뉴코드 메뉴명과 가격 주문일시 정보")
    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> findAllMenu() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("list", list);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공!!!", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @Operation(summary = "번호로 메뉴 조회"
            , parameters = { @Parameter(name = "no", description = "사용자 화면에서 넘어오는 메뉴의 pk")})
    @GetMapping("/list/{no}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int no) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MenuDTO foundUser = list.stream().filter(user -> user.getNo() == no).collect(Collectors.toList()).get(0);

        Map<String , Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회성공", responseMap));

    }
    @Operation(summary = "신규메뉴 등록")
    @PostMapping("/list")
    public ResponseEntity<?> regist(@RequestBody MenuDTO newMenu) {

        System.out.println("newMenu 잘 들어오니?? " + newMenu);

        int lastUserNo = list.get(list.size() - 1).getNo();
        newMenu.setNo(lastUserNo + 1);

        list.add(newMenu);

        return ResponseEntity
                .created(URI.create("/entity/list/" + list.get(list.size() -1).getNo()))
                .build();
    }

    @Operation(summary = "메뉴 정보 수정")
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody MenuDTO modifyInfo) {

        MenuDTO foundMenu = list.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
        foundMenu.setName(modifyInfo.getName());
        foundMenu.setPrice(modifyInfo.getPrice());

        return ResponseEntity.created(URI.create("/entity/list/" + userNo)).build();
    }

    @Operation(summary = "회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력 된 파라미터")
    })
    @DeleteMapping("/list/{no}")
    public ResponseEntity<?> removeMenu(@PathVariable int no) {

        MenuDTO foundMenu = list.stream().filter(user -> user.getNo() == no).collect(Collectors.toList()).get(0);
        list.remove(foundMenu);

        return ResponseEntity.noContent().build();
    }

}
