fn main() {
    let input = include_str!("input.txt");
    let mut sum_next_values = 0;
    let mut sum_previous_values = 0;
    for line in input.lines() {
        let mut row: Vec<i32> = Vec::new();
        for number in line.split_whitespace() {
            row.push(number.parse().unwrap());
        }

        let mut next_values: Vec<i32> = vec![*row.last().unwrap()];
        let mut previous_values: Vec<i32> = vec![*row.first().unwrap()];
        loop {
            let to_compare: Vec<i32> = row.clone().into_iter().skip(1).collect::<Vec<_>>();
            let differences: Vec<i32> = row.clone().into_iter().zip(to_compare.iter()).map(|(a, b)| b - a).collect();
            next_values.push(*differences.last().unwrap());
            previous_values.push(*differences.first().unwrap());
            let end = differences.windows(2).all(|w| w[0] == w[1]);
            if end {
                break;
            }
            row = differences.clone();
        }
        for i in (1..previous_values.len()).rev() {
            previous_values[i -  1] = previous_values[i -1] - previous_values[i];
        }
        sum_previous_values += previous_values[0];
        sum_next_values += next_values.iter().sum::<i32>();
    }
    println!("Part 1 - Sum of future values: {}", sum_next_values);
    println!("Part 2 - Sum of past values: {}", sum_previous_values);
}
