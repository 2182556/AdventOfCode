use std::collections::HashMap;

fn main() {
    let input = include_str!("input.txt");
    part_one(input);
    part_two(input);
}

fn part_one(input: &str) {
    let mut points = 0;
    for line in input.lines() {
        let input = line.split('|').collect::<Vec<_>>();
        let numbers = input[0].split(':').collect::<Vec<_>>()[1].trim().split_whitespace().collect::<Vec<_>>();
        let winning_numbers = input[1].trim().split_whitespace().collect::<Vec<_>>();
        let count = numbers.iter().filter(|&num| winning_numbers.contains(num)).count();
        if count > 0 {
            let base: i32 = 2;
            points += base.pow((count - 1).try_into().unwrap());
        }
    }
    println!("Points: {}", points);
}

fn next_card(card_number: &str, card_numbers: &HashMap<&str, Vec<i32>>) -> i32 {
    let mut number_of_cards: i32 = card_numbers[card_number].len().try_into().unwrap();
    for card in card_numbers[card_number].iter() {
        number_of_cards += next_card(&card.to_string(), card_numbers);
    }
    return number_of_cards;
}

fn part_two(input: &str) {
    let mut cards_added: HashMap<&str, Vec<i32>> = HashMap::new();
    for line in input.lines() {
        let input = line.split('|').collect::<Vec<_>>();
        let card = input[0].split(':').collect::<Vec<_>>();
        let card_number = card[0].trim().split_whitespace().collect::<Vec<_>>()[1];

        let numbers = card[1].trim().split_whitespace().collect::<Vec<_>>();
        let winning_numbers = input[1].trim().split_whitespace().collect::<Vec<_>>();
        let count = numbers.iter().filter(|&num| winning_numbers.contains(num)).count();
        cards_added.insert(card_number, (0..count).map(|i| card_number.parse::<i32>().unwrap() + (i as i32) + 1).collect::<Vec<_>>());
    }
    let mut number_of_cards = 0;
    for card in cards_added.keys() {
        number_of_cards += next_card(&card.to_string(), &cards_added) + 1;
    }
    println!("Total number of scratch cards: {}", number_of_cards);
}
