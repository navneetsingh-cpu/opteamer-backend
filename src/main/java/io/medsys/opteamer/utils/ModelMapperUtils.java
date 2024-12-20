package io.medsys.opteamer.utils;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.dto.OperationProviderDTO;
import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.OperationRoom;
import org.modelmapper.ModelMapper;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 17.
 */

public class ModelMapperUtils {

    public static OperationProvider mapOperationProviderDTOToEntity(OperationProviderDTO operationProviderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProviderDTO, OperationProvider.class);
    }

    public static OperationProviderDTO mapOperationProviderEntityToDTO(OperationProvider operationProvider) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProvider, OperationProviderDTO.class);
    }

    public static AssetDTO mapAssetEntityToDTO(Asset asset) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(asset, AssetDTO.class);
    }

    public static Asset mapAssetDTOToEntity(AssetDTO assetDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(assetDTO, Asset.class);
    }

    public static OperationRoomDTO mapOperationRoomEntityToDTO(OperationRoom operationRoom) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationRoom, OperationRoomDTO.class);
    }
}
