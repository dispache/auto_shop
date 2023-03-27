package com.company.evgeniy.auto_shop.autos;

import com.company.evgeniy.auto_shop.autos.dto.CreateAutoDto;
import com.company.evgeniy.auto_shop.autos.dto.UpdateAutoDto;
import com.company.evgeniy.auto_shop.autos.entities.AutoEntity;
import com.company.evgeniy.auto_shop.utils.MappingUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AutosService {

    private AutosRepository autosRepository;

    public AutosService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    private final MappingUtil mappingUtil = new MappingUtil();

    public Iterable<AutoEntity> getAllAutos() {
        return this.autosRepository.findAll();
    }

    public Iterable<AutoEntity> getAutosBySort(Iterable<AutoEntity> response, String sortBy, String orderBy) {
        if ( orderBy == null ) {
            orderBy = "null";
        }
        Supplier<Stream<AutoEntity>> supplier = () -> StreamSupport.stream(response.spliterator(), false);
        Stream<AutoEntity> result = null;
        if ( sortBy.equals("price") ) {
            if (orderBy.equals("desc")) {
                result = supplier.get().sorted((auto1, auto2) -> auto2.getPrice() - auto1.getPrice());
            } else if (orderBy.equals("asc")) {
                result = supplier.get().sorted((auto1, auto2) -> auto1.getPrice() - auto2.getPrice());
            }
        } else if ( sortBy.equals("productionYear") ) {
            if ( orderBy.equals("desc") ) {
               result = supplier.get().sorted((auto1, auto2) -> auto2.getProductionYear() - auto1.getProductionYear());
            } else if ( orderBy.equals("asc") ) {
               result = supplier.get().sorted((auto1, auto2) -> auto1.getProductionYear() - auto2.getProductionYear());
            }
        }
        return result == null ? response : result.collect(Collectors.toList());
    }

    public AutoEntity getAutoById(int autoId) {
        return this.autosRepository.findById(autoId).get();
    }

    public Iterable<AutoEntity> filterAutosByBrands(String brands) {
        String[] autoBrandsArray = brands.split(",");
        ArrayList<AutoEntity> result = new ArrayList<>();
        for ( String brand : autoBrandsArray ) {
            Iterable<AutoEntity> autosByBrand = this.getAutosByBrand(brand);
            result.addAll((Collection<? extends AutoEntity>) autosByBrand);
        }
        return result;
    }

    public Iterable<AutoEntity> getAutosByBrand(String brand) {
        Iterable<AutoEntity> autos = this.getAllAutos();
        return StreamSupport
                .stream(autos.spliterator(), false)
                .filter(auto -> auto.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public AutoEntity createAuto(CreateAutoDto createAutoDto) throws ParseException {
        AutoEntity autoEntity = mappingUtil.<AutoEntity, CreateAutoDto>mapDtoToEntity(createAutoDto, AutoEntity.class);
        return this.autosRepository.save(autoEntity);
    }

    public void removeAuto(int autoId) {
        this.autosRepository.deleteById(autoId);
    }

    public AutoEntity updateAuto(int autoId, UpdateAutoDto updateAutoDto) {
        AutoEntity autoEntity = this.autosRepository.findById(autoId).get();
        autoEntity.setBrand(updateAutoDto.getBrand());
        autoEntity.setModel(updateAutoDto.getModel());
        autoEntity.setColor(updateAutoDto.getColor());
        autoEntity.setFuelType(updateAutoDto.getFuelType());
        autoEntity.setProductionYear(updateAutoDto.getProductionYear());
        autoEntity.setEngineVolume(updateAutoDto.getEngineVolume());
        autoEntity.setDriveSystem(updateAutoDto.getDriveSystem());
        autoEntity.setTransmission(updateAutoDto.getTransmission());
        autoEntity.setPhoto(updateAutoDto.getPhoto());
        autoEntity.setPrice(updateAutoDto.getPrice());
        return this.autosRepository.save(autoEntity);
    }
}
