package test.connect.mssql.controller.outboundlist;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.connect.mssql.dto.ApiResponse;
import test.connect.mssql.dto.outboudlist.AddItemRequest;
import test.connect.mssql.entity.OutboundList;
import test.connect.mssql.service.outboundlist.OutBoundListService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/outboundlist")
public class OutBoundListController {

    private final OutBoundListService outBoundListService;

    @PostMapping("/addItem")
    public ResponseEntity<ApiResponse> addItem(@Validated @RequestBody AddItemRequest addItemRequest,
                                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createFail(bindingResult));
        }

        try{
            OutboundList outboundList = outBoundListService.addItem(addItemRequest);
            return ResponseEntity.status(HttpServletResponse.SC_CREATED)
                    .body(ApiResponse.createSuccess(outboundList));

        }catch (NoSuchElementException | IllegalArgumentException e){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createError(e.getMessage()));
        }

    }

    //@GetMapping("/findAll") <- 이거는 사용자 정보 추가하고 해야할 듯
}
