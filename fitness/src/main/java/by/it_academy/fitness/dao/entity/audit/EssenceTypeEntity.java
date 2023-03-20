package by.it_academy.fitness.dao.entity.audit;

import by.it_academy.fitness.core.dto.audit.enums.EssenceType;
import jakarta.persistence.*;

//@Entity
//@Table(schema = "public", name = "essenceType")
public class EssenceTypeEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private EssenceType essenceType;

    public EssenceTypeEntity(EssenceType essenceType) {
        this.essenceType = essenceType;
    }

    public EssenceTypeEntity() {
    }

    public EssenceType getEssenceType() {
        return essenceType;
    }

    public void setEssenceType(EssenceType essenceType) {
        this.essenceType = essenceType;
    }

    @Override
    public String toString() {
        return "EssenceTypeEntity{" +
                "essenceType=" + essenceType +
                '}';
    }
}
