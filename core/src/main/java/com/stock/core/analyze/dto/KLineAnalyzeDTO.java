package com.stock.core.analyze.dto;

import com.stock.core.http.ths.TendencyItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author caijinglong
 * @date 2022-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimingTendencyAnalyzeDTO implements Comparable<TimingTendencyAnalyzeDTO> {

  private String code;

  private String name;

  private List<TendencyItemModel> tendencyItemModels;

  private int order;

  @Override
  public int compareTo(TimingTendencyAnalyzeDTO timingTendencyAnalyzeDTO) {
    return Integer.compare(order, timingTendencyAnalyzeDTO.order);
  }
}
