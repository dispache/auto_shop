package com.company.evgeniy.auto_shop.autos;

import com.company.evgeniy.auto_shop.autos.dto.CreateAutoDto;
import com.company.evgeniy.auto_shop.autos.dto.UpdateAutoDto;
import com.company.evgeniy.auto_shop.autos.entities.AutoEntity;
import com.company.evgeniy.auto_shop.utils.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class AutosService {
    @Autowired
    private AutosRepository autosRepository;

    private final MappingUtil mappingUtil = new MappingUtil();

    public Iterable<AutoEntity> getAllAutos() {
        return this.autosRepository.findAll();
    }

    public AutoEntity getAutoById(int autoId) {
        return this.autosRepository.findById(autoId).get();
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
