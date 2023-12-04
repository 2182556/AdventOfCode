use std::collections::HashMap;

fn main() {
    let input = include_str!("input.txt");
    let mut points = 0;
    let base: i32 = 2;

    let mut cards_added: HashMap<i32, Vec<i32>> = HashMap::new();
    let mut number_of_cards: Vec<i32> = Vec::new();
    for line in input.lines() {
        let input = line.split('|').collect::<Vec<_>>();
        let card = input[0].split(':').collect::<Vec<_>>();
        let card_number = card[0].trim().split_whitespace().collect::<Vec<_>>()[1].parse::<i32>().unwrap();

        let numbers = card[1].trim().split_whitespace().collect::<Vec<_>>();
        let winning_numbers = input[1].trim().split_whitespace().collect::<Vec<_>>();

        let count = numbers.iter().filter(|&num| winning_numbers.contains(num)).count();
        cards_added.insert(card_number - 1, (0..count).map(|i| card_number + (i as i32)).collect::<Vec<_>>());
        number_of_cards.push(1);
        if count > 0 {
            points += base.pow((count - 1).try_into().unwrap());
        }
    }
    println!("Points: {}", points);
    for i in 0..number_of_cards.len() {
        for card in cards_added.get(&(i as i32)).unwrap() {
            number_of_cards[*card as usize] += number_of_cards[i];
        }
    }
    println!("Total number of scratch cards: {}", number_of_cards.iter().sum::<i32>());
}
