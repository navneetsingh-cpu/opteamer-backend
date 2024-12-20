package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.repositories.AssetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssetService {

    AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public Optional<AssetDTO> getAssetById(Long id) {
        try {
            Asset asset = assetRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(asset));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<AssetDTO> getAllAssets() {
        List<AssetDTO> list = new ArrayList<>();
        Iterable<Asset> allAssets = assetRepository.findAll();
        allAssets.forEach(asset -> list.add(mapEntityToDTO(asset)));
        return list;
    }

    public AssetDTO createAsset(AssetDTO assetDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Asset asset = modelMapper.map(assetDTO, Asset.class);
        asset = assetRepository.save(asset);
        return modelMapper.map(asset, AssetDTO.class);
    }

    public Optional<AssetDTO> updateAsset(Long id, AssetDTO assetDTO) {
        return assetRepository.findById(id).map(asset -> {
            asset.setType(assetDTO.getType());
            asset.setName(assetDTO.getName());
            assetRepository.save(asset);
            return mapEntityToDTO(asset);
        });
    }

    public boolean deleteAsset(Long id) {
        return assetRepository.findById(id).map(asset -> {
            assetRepository.delete(asset);
            return true;
        }).orElse(false);
    }

    private AssetDTO mapEntityToDTO(Asset asset) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(asset, AssetDTO.class);
    }
}
