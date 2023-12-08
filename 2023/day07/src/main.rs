use std::{collections::HashMap, cmp::Ordering};

fn main() {
    let input = include_str!("input.txt");
    get_solution(input, 1);
    get_solution(input, 2);
}

fn get_type(characters: HashMap<char, usize>) -> &'static str {
    match characters {
        x if x.values().any(|&value| value == 5) => "6",
        x if x.values().any(|&value| value == 4) => "5",
        x if (x.values().any(|&value| value == 3) & x.values().any(|&value| value == 2)) => "4",
        x if x.values().any(|&value| value == 3) => "3",
        x if x.values().filter(|&value| *value == 2).count() == 2 => "2",
        x if x.values().any(|&value| value == 2) => "1",
        _ => "0",
    }
}

fn get_type_1(hand: &str) -> &str {
    let mut characters: HashMap<char, usize> = HashMap::new();

    for character in hand.chars() {
        *characters.entry(character).or_insert(0) += 1;
    }
    get_type(characters)
}

fn get_type_2(hand: &str) -> &str {
    let mut characters: HashMap<char, usize> = HashMap::new();

    for character in hand.chars() {
        *characters.entry(character).or_insert(0) += 1;
    }

    let mut best_type = get_type(characters.clone());

    if characters.contains_key(&'J') {
        let mut other_characters = characters.clone();
        other_characters.remove(&'J');
        for character in other_characters.keys() {
            let mut other_characters_clone = other_characters.clone();
            *other_characters_clone.get_mut(character).unwrap() += *characters.get(&'J').unwrap();
            let new_type = get_type(other_characters_clone.clone());
            if new_type > best_type {
                best_type = new_type;
            }
        }
    }
    best_type
}

fn custom_sort_by(to_sort: &mut Vec<&str>, custom_order: &Vec<&str>) {
    to_sort.sort_by(|a, b| {
        let a_chars: Vec<_> = a.chars().collect();
        let b_chars: Vec<_> = b.chars().collect();

        for (a_char, b_char) in a_chars.iter().zip(b_chars.iter()) {
            let a_order = custom_order.iter().position(|&c| *a_char == c.chars().next().unwrap());
            let b_order = custom_order.iter().position(|&c| *b_char == c.chars().next().unwrap());

            match (a_order, b_order) {
                (Some(a_order), Some(b_order)) => {
                    if a_order != b_order {
                        return a_order.cmp(&b_order);
                    }
                }
                (Some(_), None) => return Ordering::Less,
                (None, Some(_)) => return Ordering::Greater,
                _ => {}
            }
        }

        Ordering::Equal
    });
}

fn get_solution(input: &str, part: i32) {
    let camel_cards: Vec<_> = input.lines().collect();
    let hands = camel_cards.iter().map(|x| x.split_whitespace().collect::<Vec<_>>()[0]).collect::<Vec<_>>();
    let bids = camel_cards.iter().map(|x| x.split_whitespace().collect::<Vec<_>>()[1].parse::<i32>().unwrap()).collect::<Vec<_>>();
    let hands_and_bids = hands.iter().zip(bids.iter()).collect::<Vec<_>>().iter().map(|(hand, bid)| (*hand, *bid)).collect::<HashMap<_, _>>();
    let mut hands_and_type: Vec<_> = Vec::new();
    let mut card_labels = Vec::new();
    if part == 1 {
        hands_and_type = hands.iter().zip(hands.iter().map(|hand| get_type_1(hand))).collect::<Vec<_>>();
        card_labels = vec!["2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"];
    } else if part == 2 {
        hands_and_type = hands.iter().zip(hands.iter().map(|hand| get_type_2(hand))).collect::<Vec<_>>();
        card_labels = vec!["J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q", "K", "A"];
    }
    hands_and_type.sort();

    let mut grouped_by_type: HashMap<&str, Vec<&str>> = HashMap::new();
    for (hand, type_) in hands_and_type.iter() {
        grouped_by_type.entry(type_).or_insert(Vec::new()).push(hand);
    }
    for (_, hands) in grouped_by_type.iter_mut() {
        custom_sort_by(hands, &card_labels);
    }

    let mut ordered = Vec::new();
    let mut keys: Vec<_> = grouped_by_type.keys().collect();
    keys.sort_by(|a, b| a.cmp(b));

    for key in keys {
        if let Some(values) = grouped_by_type.get(key) {
            for value in values {
                ordered.push(value);
            }
        }
    }
    
    let mut total_winnings = 0;
    for (i, hand) in ordered.iter().enumerate() {
        total_winnings += *hands_and_bids.get(hand).unwrap() * (i as i32 + 1);
    }

    println!("Part {} - Total winnings: {}", part, total_winnings);
}