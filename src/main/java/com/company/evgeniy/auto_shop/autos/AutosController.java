package com.company.evgeniy.auto_shop.autos;

import com.company.evgeniy.auto_shop.autos.dto.CreateAutoDto;
import com.company.evgeniy.auto_shop.autos.dto.UpdateAutoDto;
import com.company.evgeniy.auto_shop.autos.entities.AutoEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/autos")
@Validated
public class AutosController {
    AutosService autosService;

    AutosController(AutosService autosService) {
        this.autosService = autosService;
    }

    @GetMapping()
    public Iterable<AutoEntity> getAllAutos(@RequestParam(value = "sort_by", required = false) String sortBy,
                                            @RequestParam(value = "order_by", required = false) String orderBy) {
        if ( sortBy != null ) {
            return this.autosService.getAutosBySort(sortBy, orderBy);
        }
        return this.autosService.getAllAutos();
    }

    @GetMapping("/{id}")
    public AutoEntity getAutoById(@PathVariable("id") int autoId) {
        return this.autosService.getAutoById(autoId);
    }

    @GetMapping("/brand/{brand}")
    public Iterable<AutoEntity> getAutosByBrand(@PathVariable("brand") String brand) {
        return this.autosService.getAutosByBrand(brand);
    }

    @PostMapping("/create-auto")
    public AutoEntity createAuto(
            @RequestAttribute("dto") @Valid CreateAutoDto createAutoDto,
            @RequestParam(value = "photo", required = false) MultipartFile photoFile
    ) throws ParseException, IOException {
        if ( photoFile != null ) {
            createAutoDto.setPhoto(photoFile.getBytes());
        }
        return this.autosService.createAuto(createAutoDto);
    }

    @DeleteMapping("/remove-auto/{id}")
    public void removeAuto(@PathVariable("id") int autoId) {
        this.autosService.removeAuto(autoId);
    }

    @PutMapping("/update-auto/{id}")
    public AutoEntity updateAuto(@PathVariable("id") int autoId, @RequestBody UpdateAutoDto updateAutoDto) {
        return this.autosService.updateAuto(autoId, updateAutoDto);
    }

}
