package ru.practicum.main.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class RequestShortUpdate {

    private List<Request> conformedRequest = new ArrayList<>();

    private List<Request> canselRequest = new ArrayList<>();
}
