package wenance.challenge.apichallemge.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("btcUsdPrice")

public class BtcUsdPrice {
    @Id
    private Integer id;
    private String price;

}
